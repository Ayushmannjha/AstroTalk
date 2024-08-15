package com.example.JWTAuthenticationSpringboot.models;

public class UpdateTxRequest {
private String email;
private String txId;
private String orderId;
public String getTxId() {
	return txId;
}
public void setTxId(String txId) {
	this.txId = txId;
}
private String status;
private String txTime;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getTxTime() {
	return txTime;
}
public void setTxTime(String txTime) {
	this.txTime = txTime;
}
}
