package com.example.JWTAuthenticationSpringboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.JWTAuthenticationSpringboot.entities.AstroAdmin;
import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.repo.AstroAdminRepo;
import com.example.JWTAuthenticationSpringboot.repo.UserRepo;



@Service
public class AstroService {

@Autowired	
private UserRepo repo;
@Autowired
private AstroAdminRepo admin_repo;

@Transactional(readOnly = false)
public AstroAdmin insertAdmin(AstroAdmin admin) {
return admin_repo.save(admin);	
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


public Users getByUsernameAndPassword(String username, String password) {
	return repo.findByUsernameAndPassword(username, password);
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
