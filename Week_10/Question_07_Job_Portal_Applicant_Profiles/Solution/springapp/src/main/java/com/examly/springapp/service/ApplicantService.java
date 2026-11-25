package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ApplicantRequest;
import com.examly.springapp.model.Applicant;

public interface ApplicantService {

    Applicant createApplicant(ApplicantRequest request);

    List<Applicant> getAllApplicants();

    Applicant getApplicantById(Long id);

    Applicant updateApplicant(Long id, ApplicantRequest request);

    void deleteApplicant(Long id);
}
