package com.example.JWTAuthenticationSpringboot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JWTAuthenticationSpringboot.entities.AstroAdmin;
@Repository
public interface AstroAdminRepo extends JpaRepository<AstroAdmin, Integer>{

public List<AstroAdmin> findAll();
}
