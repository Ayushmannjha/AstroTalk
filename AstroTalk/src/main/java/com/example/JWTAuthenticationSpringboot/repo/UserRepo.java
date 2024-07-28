package com.example.JWTAuthenticationSpringboot.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JWTAuthenticationSpringboot.entities.Users;



public interface UserRepo extends JpaRepository<Users, Integer>{
public List<Users> findAll() ;
public Users findByUsernameAndPassword(String username, String password);
public Users findById(int id);
public Users findByEmail(String email);
}
