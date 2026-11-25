package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Registration;

public interface RegistrationService {

    List<Registration> getAllRegistrations();

    Registration addRegistration(Registration registration);

    boolean deleteRegistration(Long id);

    long countRegistrations();
}
