package com.example.JWTAuthenticationSpringboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.JWTAuthenticationSpringboot.entities.Astro;
import com.example.JWTAuthenticationSpringboot.entities.AstroAdmin;
import com.example.JWTAuthenticationSpringboot.entities.Transaction;
import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.repo.AstroAdminRepo;
import com.example.JWTAuthenticationSpringboot.repo.AstroRepo;
import com.example.JWTAuthenticationSpringboot.repo.TransactionRepo;
import com.example.JWTAuthenticationSpringboot.repo.UserRepo;



@Service
public class AstroService {

@Autowired	
private UserRepo repo;
@Autowired
private AstroAdminRepo admin_repo;
@Autowired
private AstroRepo astro_repo;
@Autowired
private TransactionRepo tx_repo;


public Optional<Transaction> getTxById(String id){
	return tx_repo.findById(id);
}

@Transactional(readOnly = false)
public Transaction saveTx(Transaction tr) {
	return tx_repo.save(tr);
}

public Astro getAstroById(int id) {
	return astro_repo.findById(id);
}

public List<Astro> getAllAstro(){
	return astro_repo.findAll();
}


@Transactional(readOnly = false)
public AstroAdmin insertAdmin(AstroAdmin admin) {
return admin_repo.save(admin);	
}
@Transactional(readOnly = false)
public Astro saveAstro(Astro astro) {
	return astro_repo.save(astro);
}



@Transactional(readOnly = false)
public Users insert(Users user) {
	user.setUsername(generateUsername(user.getName(),user.getPhone()));
	user.setWallet(1000);
	return repo.save(user);
}
@Transactional(readOnly = false)
public void deleteUserById(int id) {
	repo.deleteById(id);
}



public List<Users> getAll(){
return repo.findAll();
}


public Users getUserByEmailAndPassword(String email, String password) {
	return repo.findByEmailAndPassword(email, password);
}

public AstroAdmin getAdminByEmailAndPassword(String email, String password) {
	return admin_repo.findByEmailAndPassword(email, password);
}
public Astro getAstroByEmailAndPassword(String email, String password) {
	return astro_repo.findByEmailAndPassword(email, password);
}

public Users getById(int id){
	return  repo.findById(id);
}

public Users getByEmail(String email) {
	return repo.findByEmail(email);
}

public static String generateUsername(String name, String phoneNumber) {
    // Extract initials from the name
    String initials = getInitials(name);

    // Extract the last 4 digits of the phone number
    String phoneSuffix = getPhoneSuffix(phoneNumber);

    // Combine initials and phone suffix to create a username
    return initials + phoneSuffix;
}

private static String getInitials(String name) {
    String[] parts = name.split(" ");
    StringBuilder initials = new StringBuilder();
    for (String part : parts) {
        if (!part.isEmpty()) {
            initials.append(part.charAt(0));
        }
    }
    return initials.toString().toLowerCase();
}

private static String getPhoneSuffix(String phoneNumber) {
    if (phoneNumber.length() > 4) {
        return phoneNumber.substring(phoneNumber.length() - 4);
    } else {
        return phoneNumber;
    }
}





}
