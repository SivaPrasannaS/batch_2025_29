package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ApplicantRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Applicant;
import com.examly.springapp.repository.ApplicantRepository;
import com.examly.springapp.service.ApplicantService;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Applicant createApplicant(ApplicantRequest request) {
        Applicant applicant = new Applicant();
        copy(request, applicant);
        return applicantRepository.save(applicant);
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public Applicant getApplicantById(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found with id: " + id));
    }

    @Override
    public Applicant updateApplicant(Long id, ApplicantRequest request) {
        Applicant applicant = getApplicantById(id);
        copy(request, applicant);
        return applicantRepository.save(applicant);
    }

    @Override
    public void deleteApplicant(Long id) {
        applicantRepository.delete(getApplicantById(id));
    }

    private void copy(ApplicantRequest request, Applicant applicant) {
        applicant.setFullName(request.getFullName());
        applicant.setEmail(request.getEmail());
        applicant.setPhone(request.getPhone());
        applicant.setHighestQualification(request.getHighestQualification());
        applicant.setExperienceYears(request.getExperienceYears());
        applicant.setExpectedSalary(request.getExpectedSalary());
        applicant.setSkills(request.getSkills());
    }
}
