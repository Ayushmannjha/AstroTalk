package com.example.JWTAuthenticationSpringboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transaction_details")
public class Transaction {
	@Id
@Column(name="tx_id")	
private String tx_id;
@Column(name="email")
private String email;
@Column(name="tx_time")
private String tx_time;
@Column(name="status")
private String status;
@Column(name="tx_razor_id")
private String txRazorId;

public String getTxRazorId() {
	return txRazorId;
}
public void setTxRazorId(String txRazorId) {
	this.txRazorId = txRazorId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
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
public String getTx_time() {
	return tx_time;
}
public void setTx_time(String tx_time) {
	this.tx_time = tx_time;
}

}
