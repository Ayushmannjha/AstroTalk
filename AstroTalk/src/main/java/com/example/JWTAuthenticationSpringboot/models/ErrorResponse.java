package com.example.JWTAuthenticationSpringboot.models;

public class ErrorResponse {
protected boolean success;
protected String message;
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

}
