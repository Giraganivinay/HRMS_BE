package com.cg.hrms.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrms.entities.User;

public interface IUserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

}
