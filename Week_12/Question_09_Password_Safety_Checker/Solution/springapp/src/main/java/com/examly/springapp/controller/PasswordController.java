package com.examly.springapp.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.MatchRequest;
import com.examly.springapp.dto.PasswordRequest;
import com.examly.springapp.service.PasswordService;

@RestController
@RequestMapping("/api/passwords")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        return ResponseEntity.ok(Map.of("algorithm", "BCrypt"));
    }

    @PostMapping("/encode")
    public ResponseEntity<Map<String, String>> encode(@RequestBody PasswordRequest request) {
        return ResponseEntity.ok(Map.of("encodedPassword", passwordService.encode(request.getPassword())));
    }

    @PostMapping("/matches")
    public ResponseEntity<Map<String, Boolean>> matches(@RequestBody MatchRequest request) {
        return ResponseEntity.ok(Map.of("matches", passwordService.matches(request.getPassword(), request.getEncodedPassword())));
    }
}
