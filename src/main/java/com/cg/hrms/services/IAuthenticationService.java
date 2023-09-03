package com.cg.hrms.services;

import com.cg.hrms.dto.AuthenticationRequest;
import com.cg.hrms.dto.AuthenticationResponse;
import com.cg.hrms.dto.RegisterRequest;
import com.cg.hrms.entities.User;

public interface IAuthenticationService {

	AuthenticationResponse register(RegisterRequest request);

	AuthenticationResponse authenticate(AuthenticationRequest request);

	User changePassword(AuthenticationRequest request);

	User getUserById(int id);
	
	boolean deletebyid(Integer id);

	boolean signout(int id);

	User getUserDetails(String email);

}
