package com.example.JWTAuthenticationSpringboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_details")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
protected int id;
	@Column(name="name")
protected String name;
	@Column(name="phone")
protected String phone;
	@Column(name="dob")
protected String dob;
	@Column(name="gender")
protected String gender;
	@Column(name="password")
	protected String password;
	
	@Column(name="username")
	protected String username;
	@Column(name="zodiac_sign")
	protected String zodiacSign;
	@Column(name="email")
	protected String email;
	@Column(name="wallet")
	protected double wallet ;
	
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZodiacSign() {
		return zodiacSign;
	}
	public void setZodiacSign(String zodiacSign) {
		this.zodiacSign = zodiacSign;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
