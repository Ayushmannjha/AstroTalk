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

import com.example.JWTAuthenticationSpringboot.entities.Astro;
import com.example.JWTAuthenticationSpringboot.entities.AstroAdmin;
import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.models.AdminRegistrationRequest;
import com.example.JWTAuthenticationSpringboot.models.AdminRegistrationResponse;
import com.example.JWTAuthenticationSpringboot.models.AstroRegistration;
import com.example.JWTAuthenticationSpringboot.models.AstroResponse;
import com.example.JWTAuthenticationSpringboot.models.ErrorResponse;
import com.example.JWTAuthenticationSpringboot.models.LoginAdminResponse;
import com.example.JWTAuthenticationSpringboot.models.LoginAstroResponse;
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


//-----------------User registration starts-------------------------------//
    
    
    @PostMapping("/register-user") 
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
     
        
        
       
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))  // Ensure the password is encoded
                .roles("USER")  // Modify roles as needed
                .build();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(userDetails);
      
       // System.out.println(userDetails+" username=  "+newUser.getUsername()+" password = "+newUser.getPassword());
        doAuthenticate(newUser.getEmail(),newUser.getPassword());
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
   
    
  //-----------------User registration ends-------------------------------//
    
    
    
    
  //-----------------Admin registration starts-------------------------------//
    @PostMapping("/register-admin")
    public ResponseEntity<Object> registerAdmin(@RequestBody AdminRegistrationRequest request){
    	AstroAdmin newAdmin = new AstroAdmin();
    	BeanUtils.copyProperties(request, newAdmin);
    	try {
     	   astroService.insertAdmin(newAdmin);
		} catch (DataIntegrityViolationException  e) {
			
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
	            
				ErrorResponse er = new ErrorResponse();
				er.setSuccess(false);
				er.setMessage("Admin Already exits");
				return new ResponseEntity<>(er, HttpStatus.OK);
				
	        }
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Internal error");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    	UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(newAdmin.getEmail())
                .password(passwordEncoder.encode(newAdmin.getPassword()))  // Ensure the password is encoded
                .roles("ADMIN")  // Modify roles as needed
                .build();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(userDetails);
      //  System.out.println(userDetails+" username=  "+newAdmin.getUsername()+" password = "+newAdmin.getPassword());
        doAuthenticate(newAdmin.getEmail(),newAdmin.getPassword());
        String token = helper.generateToken(userDetails);
    	AdminRegistrationResponse response = new AdminRegistrationResponse();
    	response.setSuccess(true);
        response.setToken(token);
        BeanUtils.copyProperties(newAdmin, response);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    
  //-----------------User registration ends-------------------------------//
    

    
    
  //-----------------Astro registration starts-------------------------------//  
    @PostMapping("/register-astro")
    public ResponseEntity<Object> registerAstro(@RequestBody AstroRegistration request){
    	Astro newAstro = new Astro();
    	BeanUtils.copyProperties(request, newAstro);
    	try {
     	  Astro s =  astroService.saveAstro(newAstro);
     	  System.out.println(s);
		} catch (DataIntegrityViolationException  e) {
			
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
	            
				ErrorResponse er = new ErrorResponse();
				er.setSuccess(false);
				er.setMessage("Astro Already exits");
				return new ResponseEntity<>(er, HttpStatus.OK);
				
	        }
			ErrorResponse er = new ErrorResponse();
			er.setSuccess(false);
			er.setMessage("Internal error");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    	UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(newAstro.getEmail())
                .password(passwordEncoder.encode(newAstro.getPassword()))  // Ensure the password is encoded
                .roles("ASTRO")  // Modify roles as needed
                .build();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(userDetails);
       // System.out.println(userDetails+" username=  "+newAstro.getUsername()+" password = "+newAstro.getPassword());
        doAuthenticate(newAstro.getEmail(),newAstro.getPassword());
        String token = helper.generateToken(userDetails);
        AstroResponse response = new AstroResponse();
        response.setSuccess(true);
        response.setToken(token);
        BeanUtils.copyProperties(newAstro, response);
        return new ResponseEntity<>(response,HttpStatus.OK);
    	
    }
    
    
  //-----------------Astro registration starts-------------------------------//
    
    
    
    /////----------utility methods-----------------///
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



    
    
 //-----------------login starts--------------------------//
    //--single login for every role automatically find who is logging in--//////////
   
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    	
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

     // Iterate through the authorities and print the roles
        boolean isAdmin = false;
        boolean isUser = false;
        boolean isAstro = false;

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            //logic of checking role//
            if ("ROLE_ADMIN".equals(role)) {
                isAdmin = true;
            } else if ("ROLE_USER".equals(role)) {
                isUser = true;
            }
            else if("ROLE_ASTRO".equals(role)) {
            	isAstro = true;
            }
        }

      
        
        String token = this.helper.generateToken(userDetails);
        if(isAdmin) {
        	LoginAdminResponse adminResponse = new LoginAdminResponse();
        	AstroAdmin admin = astroService.getAdminByEmailAndPassword(request.getEmail(), request.getPassword());
            
        	adminResponse.setSuccess(true);
        	adminResponse.setEmail(admin.getEmail());
        	adminResponse.setRole("Admin");
        	adminResponse.setToken(token);
        	adminResponse.setUsername(admin.getUsername());
        	
        	return new ResponseEntity<>(adminResponse, HttpStatus.OK);
        }
        else if(isUser) {
        	 LoginUserResponse response = new LoginUserResponse();
        	 Users user = astroService.getUserByEmailAndPassword(request.getEmail(), request.getPassword());
             
             response.setSuccess(true);
             response.setJwtToken(token);
             response.setUsername(user.getUsername());
             response.setName(user.getName());
             response.setEmail(user.getEmail());
             return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else if(isAstro) {
        	LoginAstroResponse response = new LoginAstroResponse();
        	Astro astro = astroService.getAstroByEmailAndPassword(request.getEmail(), request.getPassword());
            
            response.setSucces(true);
            response.setToken(token);
            response.setUsername(astro.getUsername());
            response.setRole("Astrologer");
            response.setEmail(astro.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
        	ErrorResponse er = new ErrorResponse();
        	er.setSuccess(false);
        	er.setMessage("Username or password is wrong");
        	 return new ResponseEntity<>(er, HttpStatus.OK);
        }
       
    }
    
    
    //------------login ends------------------//

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, password);
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
