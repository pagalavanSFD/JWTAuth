package com.example.jwtAuthApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwtAuthApp.model.User;
import com.example.jwtAuthApp.util.JwtUtil;

@Service
public class JwtService {

    @Autowired
    private JwtUtil jwtUtil;

    // Generate token for user
    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    // Validate token
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // Extract email from token
    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }
}
