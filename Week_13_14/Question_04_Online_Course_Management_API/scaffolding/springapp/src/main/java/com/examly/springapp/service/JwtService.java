package com.examly.springapp.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.examly.springapp.model.User;

public interface JwtService {

    String generateToken(User user);

    long getExpirationMs();

    String extractUsername(String token);

    String extractRole(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
