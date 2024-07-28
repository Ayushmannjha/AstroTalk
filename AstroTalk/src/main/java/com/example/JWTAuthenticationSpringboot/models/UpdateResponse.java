package com.example.JWTAuthenticationSpringboot.models;

public class UpdateResponse {
private boolean success;
private String message;
private int id;
public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public int getId() {
	return id;
}
public void setId(int i) {
	this.id = i;
}
}
