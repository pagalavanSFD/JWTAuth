package com.example.jwtAuthApp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.jwtAuthApp.model.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // Extract the email from the token
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // Extract claims from the token
    private Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Generate the token
    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject(user.getEmail())  // Set the subject as the user's email
                .claim("name", user.getName())  // Add the name as a claim
                .setIssuedAt(new Date())  // Set the issued date
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))  // Set expiration time
                .signWith(key, SignatureAlgorithm.HS256)  // Sign the token using the secret and algorithm
                .compact();
    }
}
