package com.examly.springapp.service;

public interface PasswordService {

    String encode(String password);

    boolean matches(String password, String encodedPassword);
}
