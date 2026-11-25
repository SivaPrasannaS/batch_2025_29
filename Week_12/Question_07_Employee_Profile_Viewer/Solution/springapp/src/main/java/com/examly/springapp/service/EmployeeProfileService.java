package com.examly.springapp.service;

import com.examly.springapp.model.EmployeeProfile;

public interface EmployeeProfileService {

    EmployeeProfile saveProfile(String username, EmployeeProfile profile);

    EmployeeProfile getProfile(String username);
}
