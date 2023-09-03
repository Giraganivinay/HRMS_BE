package com.cg.hrms.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.dto.UpdateRequest;
import com.cg.hrms.entities.User;
import com.cg.hrms.repos.IUserRepo;


@Service
public class UserService {

	@Autowired
	private IUserRepo userRepository;


	public User getUserDetails(String email) {

		Optional<User> user = userRepository.findByEmail(email);

		return user.get();
	}

	public boolean updateUser(int id, UpdateRequest user) {
		User u1 = userRepository.findById(id).get();

		if (u1 != null) {

			u1.setEmail(user.getEmail());
			u1.setFirstname(user.getFirstname());
			u1.setLastname(user.getLastname());

			userRepository.save(u1);
			return true;
		}

		return false;

	}


}
