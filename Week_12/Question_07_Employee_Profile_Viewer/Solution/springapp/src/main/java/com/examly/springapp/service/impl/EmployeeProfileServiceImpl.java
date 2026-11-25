package com.examly.springapp.service.impl;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.EmployeeProfile;
import com.examly.springapp.repository.EmployeeProfileRepository;
import com.examly.springapp.service.EmployeeProfileService;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository profileRepository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public EmployeeProfile saveProfile(String username, EmployeeProfile profile) {
        EmployeeProfile existing = profileRepository.findByUsername(username).orElse(new EmployeeProfile());
        existing.setUsername(username);
        existing.setFullName(profile.getFullName());
        existing.setDepartment(profile.getDepartment());
        existing.setDesignation(profile.getDesignation());
        return profileRepository.save(existing);
    }

    @Override
    public EmployeeProfile getProfile(String username) {
        return profileRepository.findByUsername(username).orElse(null);
    }
}
