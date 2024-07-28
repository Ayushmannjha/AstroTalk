package com.example.JWTAuthenticationSpringboot.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.models.ErrorResponse;
import com.example.JWTAuthenticationSpringboot.models.LoginAdminResponse;
import com.example.JWTAuthenticationSpringboot.models.LoginRequest;
import com.example.JWTAuthenticationSpringboot.models.LoginUserResponse;
import com.example.JWTAuthenticationSpringboot.models.RegistrationRequest;
import com.example.JWTAuthenticationSpringboot.models.RegistrationResponse;
import com.example.JWTAuthenticationSpringboot.security.JWTHelper;
import com.example.JWTAuthenticationSpringboot.service.AstroService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private AstroService astroService;
    
    
    @Autowired
    private JWTHelper helper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    
    @PostMapping("/register") 
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest request) {
    	
    	Users newUser = new Users();
        String dateTimeString  = request.getDob();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        BeanUtils.copyProperties(request, newUser);
        
        String zodiacSign = determineZodiacSign(localDateTime.toLocalDate());
        newUser.setZodiacSign(zodiacSign);
        
        try {
        	   astroService.insert(newUser);
		} catch (DataIntegrityViolationException  e) {
			
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
	            
				ErrorResponse er = new ErrorResponse();
				er.setSuccess(false);
				er.setMessage("User Already exits");
				return new ResponseEntity<>(er, HttpStatus.OK);
				
	        }
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Internal error");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
     
        
        
       
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))  // Ensure the password is encoded
                .roles("USER")  // Modify roles as needed
                .build();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(userDetails);
      
        System.out.println(userDetails+" username=  "+newUser.getUsername()+" password = "+newUser.getPassword());
        doAuthenticate(newUser.getUsername(),newUser.getPassword());
        String token = helper.generateToken(userDetails);
        RegistrationResponse response = new RegistrationResponse();
        response.setToken(token);
        response.setEmail(request.getEmail());
        response.setPassword(request.getPassword());
        response.setDob(request.getDob());
        response.setPhone(request.getPhone());
        response.setWallet(newUser.getWallet());
        response.setSuccess(true);
        response.setZodiacSign(newUser.getZodiacSign());
        response.setUsername(userDetails.getUsername());
    	
    	
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
   
    
    public boolean verifyZodiacSign(LocalDate birthDate, String providedZodiacSign) {
        String calculatedZodiacSign = determineZodiacSign(birthDate);
        return calculatedZodiacSign.equalsIgnoreCase(providedZodiacSign);
    }

    public String determineZodiacSign(LocalDate birthDate) {
        int day = birthDate.getDayOfMonth();
        int month = birthDate.getMonthValue();

        switch (month) {
            case 1:  return (day <= 19) ? "Capricorn" : "Aquarius";
            case 2:  return (day <= 18) ? "Aquarius" : "Pisces";
            case 3:  return (day <= 20) ? "Pisces" : "Aries";
            case 4:  return (day <= 19) ? "Aries" : "Taurus";
            case 5:  return (day <= 20) ? "Taurus" : "Gemini";
            case 6:  return (day <= 20) ? "Gemini" : "Cancer";
            case 7:  return (day <= 22) ? "Cancer" : "Leo";
            case 8:  return (day <= 22) ? "Leo" : "Virgo";
            case 9:  return (day <= 22) ? "Virgo" : "Libra";
            case 10: return (day <= 22) ? "Libra" : "Scorpio";
            case 11: return (day <= 21) ? "Scorpio" : "Sagittarius";
            case 12: return (day <= 21) ? "Sagittarius" : "Capricorn";
            default: throw new IllegalArgumentException("Invalid month: " + month);
        }
    }



    
    
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    	
        this.doAuthenticate(request.getUsername(), request.getPassword());

        Users user = astroService.getByUsernameAndPassword(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

     // Iterate through the authorities and print the roles
        boolean isAdmin = false;
        boolean isUser = false;

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                isAdmin = true;
            } else if ("ROLE_USER".equals(role)) {
                isUser = true;
            }
        }

      
        
        String token = this.helper.generateToken(userDetails);
        if(isAdmin) {
        	LoginAdminResponse adminResponse = new LoginAdminResponse();
        	adminResponse.setSuccess(true);
        	adminResponse.setEmail("adminDatabaseNotPrepared");
        	adminResponse.setRole("Admin");
        	adminResponse.setToken(token);
        	adminResponse.setUsername(request.getUsername());
        	
        	return new ResponseEntity<>(adminResponse, HttpStatus.OK);
        }
        else if(isUser) {
        	 LoginUserResponse response = new LoginUserResponse();
             response.setSuccess(true);
             response.setJwtToken(token);
             response.setUsername(userDetails.getUsername());
             response.setEmail(user.getEmail());
             return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
        	ErrorResponse er = new ErrorResponse();
        	er.setSuccess(false);
        	er.setMessage("Username or password is wrong");
        	 return new ResponseEntity<>(er, HttpStatus.OK);
        }
       
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
        	e.printStackTrace();
        	
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    
  

    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse exceptionHandler() {
    	ErrorResponse er = new ErrorResponse();
    	er.setSuccess(false);
    	er.setMessage("Invalid Username or password");
        return er;
    }
}
