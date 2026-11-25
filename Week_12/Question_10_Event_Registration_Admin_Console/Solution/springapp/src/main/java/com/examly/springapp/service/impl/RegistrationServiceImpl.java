package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Registration;
import com.examly.springapp.repository.RegistrationRepository;
import com.examly.springapp.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public Registration addRegistration(Registration registration) {
        registration.setId(null);
        return registrationRepository.save(registration);
    }

    @Override
    public boolean deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            return false;
        }
        registrationRepository.deleteById(id);
        return true;
    }
    @Override
    public long countRegistrations() {
        return registrationRepository.count();
    }
}
