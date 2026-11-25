package com.examly.springapp.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.EmployeeProfile;
import com.examly.springapp.service.EmployeeProfileService;

@RestController
@RequestMapping("/api")
public class EmployeeProfileController {

    private final EmployeeProfileService profileService;

    public EmployeeProfileController(EmployeeProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).sorted().toList();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("username", authentication.getName());
        body.put("roles", roles);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeProfile> saveMyProfile(Authentication authentication, @RequestBody EmployeeProfile profile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.saveProfile(authentication.getName(), profile));
    }

    @GetMapping("/employees/me")
    public ResponseEntity<EmployeeProfile> getMyProfile(Authentication authentication) {
        EmployeeProfile profile = profileService.getProfile(authentication.getName());
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }
}
