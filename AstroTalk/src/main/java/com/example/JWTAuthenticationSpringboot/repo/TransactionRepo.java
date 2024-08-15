package com.example.JWTAuthenticationSpringboot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JWTAuthenticationSpringboot.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, String>{

public Optional<Transaction> findById(String id);
}