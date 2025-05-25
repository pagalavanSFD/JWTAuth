package com.example.jwtAuthApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtAuthApp.dto.AuthResponse;
import com.example.jwtAuthApp.dto.LoginRequest;
import com.example.jwtAuthApp.dto.RegisterRequest;
import com.example.jwtAuthApp.service.JwtService;
import com.example.jwtAuthApp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;

	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request) {
		return userService.registerUser(request);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request) {
	    return userService.loginUser(request);
	}
	
	@GetMapping("/api/user/me")
	public ResponseEntity<String> me() {
	    return ResponseEntity.ok("Welcome, Batman!");
	}


	
}
