package com.cg.hrms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrms.dto.AuthenticationRequest;
import com.cg.hrms.dto.AuthenticationResponse;
import com.cg.hrms.dto.RegisterRequest;
import com.cg.hrms.dto.UpdateRequest;
import com.cg.hrms.entities.User;
import com.cg.hrms.services.AuthenticationService;
import com.cg.hrms.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService service;
	

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register( @RequestBody RegisterRequest request){
		return new ResponseEntity<>(service.register(request), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
	}
	
	@PutMapping("/logout/{id}")
	public ResponseEntity<Boolean> signout(@PathVariable Integer id){
		return new ResponseEntity<>(service.signout(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<Boolean> deletebyId(@PathVariable Integer id){
		return new ResponseEntity<>(service.deletebyid(id), HttpStatus.OK);
	}
	
	@PutMapping("/changepassword")
	public ResponseEntity<User> changePassword(@RequestBody AuthenticationRequest request){
		return new ResponseEntity<>(service.changePassword(request), HttpStatus.OK);
	}
	
	@GetMapping("/getuserbyid/{id}")
	public ResponseEntity<User> getById(@PathVariable Integer id){
		return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
	}
	
	@PutMapping("/updateprofile/{id}")
	public ResponseEntity<Boolean> updateProfile(@PathVariable Integer id, @RequestBody UpdateRequest user){
		return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
	}
	
	@GetMapping("/user/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email){
		return new ResponseEntity<>(userService.getUserDetails(email), HttpStatus.OK);
	}

}








































