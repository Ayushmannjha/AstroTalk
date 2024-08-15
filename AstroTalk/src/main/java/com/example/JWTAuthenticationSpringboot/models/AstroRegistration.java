package com.example.JWTAuthenticationSpringboot.models;

public class AstroRegistration {
private String username;
private String email;
private String password;
private String phone;
private double experience;
private String category;
private String gender;
private String bio;
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
public double getExperience() {
	return experience;
}
public void setExperience(double experience) {
	this.experience = experience;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getBio() {
	return bio;
}
public void setBio(String bio) {
	this.bio = bio;
}
}
