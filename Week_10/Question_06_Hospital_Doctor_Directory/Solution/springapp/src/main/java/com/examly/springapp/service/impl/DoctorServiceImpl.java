package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.DoctorRequest;
import com.examly.springapp.exception.DuplicateResourceException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Doctor;
import com.examly.springapp.repository.DoctorRepository;
import com.examly.springapp.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor addDoctor(DoctorRequest request) {
        if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw duplicate(request.getLicenseNumber());
        }
        Doctor doctor = new Doctor();
        copy(request, doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    @Override
    public Doctor updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = getDoctorById(id);
        if (doctorRepository.existsByLicenseNumberAndIdNot(request.getLicenseNumber(), id)) {
            throw duplicate(request.getLicenseNumber());
        }
        copy(request, doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    private DuplicateResourceException duplicate(String licenseNumber) {
        return new DuplicateResourceException("Doctor already exists with license number: " + licenseNumber);
    }

    private void copy(DoctorRequest request, Doctor doctor) {
        doctor.setDoctorName(request.getDoctorName());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setConsultationFee(request.getConsultationFee());
    }
}
