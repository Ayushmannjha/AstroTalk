package com.example.JWTAuthenticationSpringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.models.ErrorResponse;
import com.example.JWTAuthenticationSpringboot.service.AstroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/astro")
public class AstrologerController {
@Autowired	
private AstroService sr;


@GetMapping("/get-users")
public ResponseEntity<Object> getAllUsers(){
	try {
		List<Users> response = sr.getAll();
		
		return new ResponseEntity<Object>(response,HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		ErrorResponse er = new ErrorResponse();
		er.setSuccess(false);
		er.setMessage("Internal server error");
		return new ResponseEntity<Object>(er,HttpStatus.OK);
	}
	
}

@GetMapping("/get-user")
public ResponseEntity<Object> getUser(@RequestParam int id){
	try {
		Users response = sr.getById(id);
		if(response==null) {
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("user not exist");
			return new ResponseEntity<Object>(er,HttpStatus.OK);
		}
		return new ResponseEntity<Object>(response,HttpStatus.OK);
	} catch (Exception e) {
		ErrorResponse er = new ErrorResponse();
		er.setSuccess(false);
		er.setMessage("Internal server error");
		return new ResponseEntity<Object>(er,HttpStatus.OK);
	}
	
}


}
