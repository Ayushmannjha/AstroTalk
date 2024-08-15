package com.example.JWTAuthenticationSpringboot.models;

public class TransactionRequest {
private double amount;
private String email;
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
}
