package com.example.JWTAuthenticationSpringboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="astro_admin")
public class AstroAdmin {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")	
protected int id;
@Column(name="username")
protected String username;
@Column(name="email")
protected String email;
@Column(name="phone")
protected String phone;
@Column(name="password")
protected String password;
@Column(name="gender")
protected String gender;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
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

}
