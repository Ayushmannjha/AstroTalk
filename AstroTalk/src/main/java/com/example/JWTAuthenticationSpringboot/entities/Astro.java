package com.example.JWTAuthenticationSpringboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="astro")
public class Astro {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")	
private int id;
@Column(name="username")
private String username;
@Column(name="email")
private String email;
@Column(name="phone")
private String phone;
@Column(name="password")
private String password;
@Column(name="experience")
private double experience;
@Column(name="category")
private String category;
@Column(name="gender")
private String gender;
@Column(name="bio")
private String bio;
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
