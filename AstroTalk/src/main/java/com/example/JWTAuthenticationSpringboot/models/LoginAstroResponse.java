package com.example.JWTAuthenticationSpringboot.models;

public class LoginAstroResponse {
private String username;
private boolean succes;
private String email;
private String role;
private String token;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public boolean isSucces() {
	return succes;
}
public void setSucces(boolean succes) {
	this.succes = succes;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
}
