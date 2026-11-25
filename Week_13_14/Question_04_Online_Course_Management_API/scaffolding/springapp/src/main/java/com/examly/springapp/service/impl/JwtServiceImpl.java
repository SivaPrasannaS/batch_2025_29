package com.examly.springapp.service.impl;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.service.JwtService;

import io.jsonwebtoken.Claims;import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    @Override
    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public long getExpirationMs() {
        return expirationMs;
    }

    @Override
    public String extractUsername(String token) {
        // TODO: Return the subject (email) of a token whose signature is valid.
        return null;
    }

    @Override
    public String extractRole(String token) {
        // TODO: Return the role claim of a token whose signature is valid.
        return null;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // TODO: Return true only when the token belongs to this user and has not expired.
        return false;
    }


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
