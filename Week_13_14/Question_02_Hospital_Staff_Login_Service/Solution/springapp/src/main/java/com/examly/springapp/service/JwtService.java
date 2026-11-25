package com.examly.springapp.service;

import com.examly.springapp.model.User;

public interface JwtService {

    String generateToken(User user);

    long getExpirationMs();
}
