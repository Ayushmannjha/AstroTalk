package com.example.JWTAuthenticationSpringboot.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.models.ErrorResponse;
import com.example.JWTAuthenticationSpringboot.models.ProfileResponse;
import com.example.JWTAuthenticationSpringboot.models.UpdateRequest;
import com.example.JWTAuthenticationSpringboot.models.UpdateResponse;
import com.example.JWTAuthenticationSpringboot.service.AstroService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
	private AstroService sr;

    // localhost:8081/home/user
    @GetMapping("/profile")
    public ResponseEntity<Object> profile(@RequestParam("id")String id){
    	int ids ;
        try {
        	ids = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Invalid userId");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    	
    	Users user = sr.getById(ids);
    	ProfileResponse profile = new ProfileResponse();
    	profile.setUsername(user.getUsername());
    	profile.setName(user.getName());
    	profile.setEmail(user.getEmail());
    	profile.setPhone(user.getPhone());
        profile.setDob(user.getDob());
        profile.setPassword(user.getPassword());
        profile.setGender(user.getGender());
        profile.setWallet(user.getWallet());
        
        return new ResponseEntity<>(profile,HttpStatus.OK);
    }

    @PutMapping("/profile-update")
    public ResponseEntity<Object> update(@RequestBody UpdateRequest request){
       
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
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Failed to update profile");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    	
        
        
        
        
    }
}
