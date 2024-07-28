package com.example.JWTAuthenticationSpringboot.models;

public class RegistrationResponse {
	protected boolean success;
	protected String username;
	protected String email;
	protected String password;
	protected String phone;
	protected String dob;
	protected String token;
	protected double wallet;
	protected String zodiacSign;

	public String getZodiacSign() {
		return zodiacSign;
	}
	public void setZodiacSign(String zodiacSign) {
		this.zodiacSign = zodiacSign;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double d) {
		this.wallet = d;
	}
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	}

