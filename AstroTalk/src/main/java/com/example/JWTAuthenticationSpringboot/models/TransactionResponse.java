package com.example.JWTAuthenticationSpringboot.models;

public class TransactionResponse {
private boolean sucess;	
private String tx_id;
private String email;
private double amount;
private String orderId;
private String status;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public boolean isSucess() {
	return sucess;
}
public void setSucess(boolean sucess) {
	this.sucess = sucess;
}
public String getTx_id() {
	return tx_id;
}
public void setTx_id(String tx_id) {
	this.tx_id = tx_id;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
}
