package com.example.JWTAuthenticationSpringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.models.ErrorResponse;
import com.example.JWTAuthenticationSpringboot.models.UpdateRequest;
import com.example.JWTAuthenticationSpringboot.models.UpdateResponse;
import com.example.JWTAuthenticationSpringboot.service.AstroService;

@RestController
@RequestMapping("/admin")
public class AdminController {
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
			
			return new ResponseEntity<Object>(response,HttpStatus.OK);
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Internal server error");
			return new ResponseEntity<Object>(er,HttpStatus.OK);
		}
		
	}
	
	@PutMapping("/update-user")
	public ResponseEntity<Object> update(@RequestBody UpdateRequest request){
		System.out.println(request);
		try {
    		Users user = sr.getByEmail(request.getEmail());
            user.setName(request.getName());
            user.setGender(request.getGender());
            user.setPhone(request.getPhone());
            
            user.setDob(request.getDob());
            user.setPassword(request.getPassword());
            user.setZodiacSign(request.getZodiacSign());
            
            sr.insert(user);
            
            UpdateResponse response = new UpdateResponse();
            response.setId(user.getId());
            response.setMessage("profile updated succesfully");
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Failed to update profile");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    	
	}
	
	@DeleteMapping("/delete-user")
	public ResponseEntity<Object> deleteUser(@RequestParam int id){
		try {
			 sr.deleteUserById(id);
			 ErrorResponse er = new ErrorResponse();
				er.setSuccess(true);
				er.setMessage("User deleted successfully");
			return new ResponseEntity<Object>(er,HttpStatus.OK);
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Internal server error");
			return new ResponseEntity<Object>(er,HttpStatus.OK);
		}
}
	
	
}
