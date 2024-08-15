package com.example.JWTAuthenticationSpringboot.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JWTAuthenticationSpringboot.entities.Astro;

public interface AstroRepo extends JpaRepository<Astro, Integer>{

public Astro findById(int id);
public List<Astro> findAll();
public Astro findByEmailAndPassword(String email, String password);
}
