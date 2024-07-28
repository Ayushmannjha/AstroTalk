package com.example.JWTAuthenticationSpringboot.models;

public class RegistrationRequest {
protected String name;	
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
protected String username;
protected String email;
protected String password;
protected String phone;
protected String dob;
protected String gender;
protected String zodiacSign;

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
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getZodiacSign() {
	return zodiacSign;
}
public void setZodiacSign(String zodiacSign) {
	this.zodiacSign = zodiacSign;
}
}
