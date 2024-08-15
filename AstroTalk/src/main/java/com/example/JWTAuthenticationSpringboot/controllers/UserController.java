package com.example.JWTAuthenticationSpringboot.controllers;



import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWTAuthenticationSpringboot.entities.Transaction;
import com.example.JWTAuthenticationSpringboot.entities.Users;
import com.example.JWTAuthenticationSpringboot.models.ErrorResponse;
import com.example.JWTAuthenticationSpringboot.models.ProfileResponse;
import com.example.JWTAuthenticationSpringboot.models.TransactionRequest;
import com.example.JWTAuthenticationSpringboot.models.TransactionResponse;
import com.example.JWTAuthenticationSpringboot.models.UpdateRequest;
import com.example.JWTAuthenticationSpringboot.models.UpdateResponse;
import com.example.JWTAuthenticationSpringboot.models.UpdateTxRequest;
import com.example.JWTAuthenticationSpringboot.service.AstroService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

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
    
    
    //---------------Transaction ------------------------//
    @PostMapping("/pay")
    public ResponseEntity<Object> transactionCreation(@RequestBody TransactionRequest request){
    	 	try {
    	 		RazorpayClient client = new RazorpayClient("rzp_test_jvOxflopH7yhOH","WPAiqqRHMk5uBSsmDBDJwq1D");
    	 		TransactionResponse response = new TransactionResponse();
    	 		String genratedId = generateRandomString(7);
    	 		JSONObject option = new JSONObject();
    	 		option.put("amount", request.getAmount()*100);
    	 		option.put("currency", "INR");
    	 		option.put("receipt", genratedId);
    	 		
    	 		Order order = client.orders.create(option);
    	 		
    	 		
    	 		response.setSucess(true);
    	 		response.setEmail(request.getEmail());
    	 		response.setTx_id(genratedId);
    	 		response.setAmount(request.getAmount()*100);
    	 		response.setOrderId(order.toString());
    	 		response.setStatus("Payment Order placed");
    	 		
    	 		//saving transction details in database starts//
    	 		Transaction tr = new Transaction();
    	 		tr.setEmail(request.getEmail());
    	 		tr.setTx_id(genratedId);
    	 		LocalDateTime now = LocalDateTime.now();

    	        // Convert LocalDateTime to java.sql.Timestamp
    	        Timestamp timestamp = Timestamp.valueOf(now);
    	 		
    	 		tr.setTx_time(timestamp.toString());
    	 		tr.setStatus("Pending");
    	 		sr.saveTx(tr);
    	 		//saving transction details in database ends//
    	 		return new ResponseEntity<>(response, HttpStatus.OK);
    	 		
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse();
			e.printStackTrace();
			er.setSuccess(false);
			er.setMessage("failed to placed payment  order");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    }
    
    @PutMapping("/update-tx")
    public ResponseEntity<Object> updateTx(@RequestBody UpdateTxRequest request){
    	try {
			Optional<Transaction> tr = sr.getTxById(request.getTxId());
			if (tr.isPresent()) {
			    Transaction transaction = tr.get();
			    transaction.setStatus(request.getStatus());
			    transaction.setTxRazorId(request.getOrderId());
			    transaction.setTx_time(request.getTxTime());
			    sr.saveTx(transaction);
			    return new ResponseEntity<>(transaction, HttpStatus.OK);
			} else {
			    // Handle the case where the transaction is not found
				ErrorResponse er = new ErrorResponse();
				
				er.setSuccess(false);
				er.setMessage("order not found");
				return new ResponseEntity<>(er, HttpStatus.OK);
			}
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse();
			e.printStackTrace();
			er.setSuccess(false);
			er.setMessage("failed to Update payment  order");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
    }
    
    
    
    public  String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
 
    
    
}
