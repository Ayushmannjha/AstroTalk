package com.example.JWTAuthenticationSpringboot.models;

public class LoginAdminResponse {
private boolean success;
private String username;
private String role;
private String email;
private String token;
public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
}
