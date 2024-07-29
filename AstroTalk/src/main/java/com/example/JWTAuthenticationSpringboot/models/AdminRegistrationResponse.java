package com.example.JWTAuthenticationSpringboot.models;

public class AdminRegistrationResponse {
private boolean success;
private String username;
private String email;
private String password;
private String phone;
private String gender;
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
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
}
