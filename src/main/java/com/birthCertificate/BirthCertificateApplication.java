package com.birthCertificate;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.birthCertificate.config.AppConstants;
import com.birthCertificate.entities.Role;
import com.birthCertificate.repositories.RoleRepo;

@SpringBootApplication
public class BirthCertificateApplication {

	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(BirthCertificateApplication.class, args);
		
		
	}

}
