package com.birthCertificate.controllers;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.birthCertificate.config.TokenBlacklistService;
import com.birthCertificate.entities.User;
import com.birthCertificate.exceptions.ApiException;
import com.birthCertificate.payloads.JwtAuthRequest;
import com.birthCertificate.payloads.JwtAuthResponse;
import com.birthCertificate.payloads.UserDto;
import com.birthCertificate.repositories.UserRepo;
import com.birthCertificate.security.CustomUserDetailService;
import com.birthCertificate.security.JwtTokenHelper;
import com.birthCertificate.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private TokenBlacklistService tokenBlacklistService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {


			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}


	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

	// get loggedin user data
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/current-user/")
	public ResponseEntity<UserDto> getUser(Principal principal) {
		User user = this.userRepo.findByEmail(principal.getName()).get();
		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
	}
	
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
	    // Extract token from the Authorization header (Bearer scheme)
	    if (token != null && token.startsWith("Bearer ")) {
	        token = token.substring(7);
	    } else {
	        return new ResponseEntity<>("Token is missing", HttpStatus.BAD_REQUEST);
	    }

	    // Add token to blacklist
	    tokenBlacklistService.addToBlacklist(token);
	    return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
	}
	
	
	@PostMapping("/forgot-password")
	public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam("email") String email) {
	    UserDetails user = customUserDetailService.loadUserByUsername(email);
	    if (user == null) {
	        return new ResponseEntity<>(Map.of("message", "User with email not found"), HttpStatus.NOT_FOUND);
	    }

	    // Generate the token
	    String token = jwtTokenHelper.generateToken(user);

	    // Return the token in the response
	    return new ResponseEntity<>(Map.of("token", token, "message", "Use this token to reset your password"), HttpStatus.OK);
	}
	
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam("token") String token, 
	                                            @RequestParam("newPassword") String newPassword) {
	    // Step 1: Validate the token
		String username;
	    try {
	        username = jwtTokenHelper.getUsernameFromToken(token);
	        System.out.println("Extracted username: " + username);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
	    }

	    // Step 2: Find the user using the token
	    UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
	    if (userDetails == null) {
	        return new ResponseEntity<>("Invalid token or user not found", HttpStatus.BAD_REQUEST);
	    }

	    // Step 3: Update the user's password
	    User user = userRepo.findByEmail(username).orElse(null);
	    if (user == null) {
	        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	    }

	    // Hash the new password before saving it
	    String encodedPassword = passwordEncoder.encode(newPassword);
	    user.setPassword(encodedPassword);
	    userRepo.save(user);

	    // Step 4: Respond to the client
	    return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
	}

	
	

}
