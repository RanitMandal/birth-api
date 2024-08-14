package com.birthCertificate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birthCertificate.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
		
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByName(String name);
}
