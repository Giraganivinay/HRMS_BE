package com.cg.hrms.services;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.hrms.dto.AuthenticationRequest;
import com.cg.hrms.dto.AuthenticationResponse;
import com.cg.hrms.dto.RegisterRequest;
import com.cg.hrms.entities.Role;
import com.cg.hrms.entities.User;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IUserRepo;
import com.cg.hrms.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{

	private final IUserRepo userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	@Override
	public AuthenticationResponse register(RegisterRequest request) {

		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.valueOf(request.getRole().toUpperCase()))
				.build();

		Optional<User> getUser = userRepository.findByEmail(request.getEmail());

		if (getUser.isEmpty()) {
			userRepository.save(user);
			var jwtToken = jwtService.generateToken(user);
			return AuthenticationResponse.builder().jwtToken(jwtToken).username(user.getFirstname()).role(user.getRole().toString()).build();
		} else {
			throw new CustomException("User has already registered");
		}

	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {

		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new CustomException("User not found with email :" + request.getEmail()));


			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			user.setLogged(true);
			userRepository.save(user);

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {

					user.setLogged(false);
					userRepository.save(user);

				}
			}, 1000 * 60 * 120);

			var jwtToken = jwtService.generateToken(user);

			return AuthenticationResponse.builder().jwtToken(jwtToken).username(user.getFirstname()).role(user.getRole().toString()).build(); 
	}

	@Override
	public User changePassword(AuthenticationRequest request) {

		Optional<User> user2 = userRepository.findByEmail(request.getEmail());
		User user = user2.get();

		if (user2.isPresent()) {

			user.setPassword(passwordEncoder.encode(request.getPassword()));

		} else {
			throw new CustomException("User not found with email :" + request.getEmail());
		}
		return userRepository.save(user);
	}

	@Override
	public User getUserById(int id) {

		return userRepository.findById(id).orElseThrow(() -> new CustomException("User not found !!"));
	}

	@Override
	public boolean deletebyid(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.getById(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean signout(int id) {

		Optional<User> user = userRepository.findById(id);
		User u1 = user.get();
		if (u1 != null) {
			if (u1.isLogged()) {
				u1.setLogged(false);
				userRepository.save(u1);
				return true;
			}

		}
		return false;

	}
	
	@Override
	public User getUserDetails(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.get();
	}

}
