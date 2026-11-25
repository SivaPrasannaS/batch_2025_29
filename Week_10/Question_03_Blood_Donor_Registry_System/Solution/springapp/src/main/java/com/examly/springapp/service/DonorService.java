package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.DonorRequest;
import com.examly.springapp.model.Donor;

public interface DonorService {

    Donor registerDonor(DonorRequest request);

    List<Donor> getAllDonors();

    Donor getDonorById(Long id);

    Donor updateDonor(Long id, DonorRequest request);
}
