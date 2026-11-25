package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.DoctorRequest;
import com.examly.springapp.model.Doctor;

public interface DoctorService {

    Doctor addDoctor(DoctorRequest request);

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    Doctor updateDoctor(Long id, DoctorRequest request);

    List<Doctor> getDoctorsBySpecialization(String specialization);
}
