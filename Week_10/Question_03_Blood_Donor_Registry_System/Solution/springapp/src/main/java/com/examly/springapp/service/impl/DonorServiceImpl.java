package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.DonorRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Donor;
import com.examly.springapp.repository.DonorRepository;
import com.examly.springapp.service.DonorService;

@Service
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;

    public DonorServiceImpl(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Override
    public Donor registerDonor(DonorRequest request) {
        Donor donor = new Donor();
        copy(request, donor);
        return donorRepository.save(donor);
    }

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    @Override
    public Donor updateDonor(Long id, DonorRequest request) {
        Donor donor = getDonorById(id);
        copy(request, donor);
        return donorRepository.save(donor);
    }

    private void copy(DonorRequest request, Donor donor) {
        donor.setDonorName(request.getDonorName());
        donor.setBloodGroup(request.getBloodGroup());
        donor.setAge(request.getAge());
        donor.setWeightKg(request.getWeightKg());
        donor.setPreviousDonations(request.getPreviousDonations());
        donor.setCity(request.getCity());
    }
}
