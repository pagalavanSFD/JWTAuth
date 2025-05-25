package com.example.jwtAuthApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtAuthApp.dto.AuthResponse;
import com.example.jwtAuthApp.dto.LoginRequest;
import com.example.jwtAuthApp.dto.RegisterRequest;
import com.example.jwtAuthApp.model.User;
import com.example.jwtAuthApp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	  @Autowired
	    private JwtService jwtService;
	
	public String registerUser(RegisterRequest request) {
		
		// Step 1: check if email already exists
		if(userRepository.findByEmail(request.getEmail()).isPresent()) {
			return "Email is already  registered";
		}
		
		//Step 2: Create and Populate user
		
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		// Step 3: save user
		userRepository.save(user);
		
		return "User Registered Succesfully";
	}

    public AuthResponse loginUser(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

}
