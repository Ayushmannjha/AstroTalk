package com.example.JWTAuthenticationSpringboot.models;

public class UpdateRequest {
private String name;
private String email;
private String phone;
private String dob;
private String password;
private String gender;
private String zodiacSign;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
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
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
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
