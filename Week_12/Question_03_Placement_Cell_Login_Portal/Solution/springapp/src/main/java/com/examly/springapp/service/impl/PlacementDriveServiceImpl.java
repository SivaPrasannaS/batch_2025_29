package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.PlacementDrive;
import com.examly.springapp.repository.PlacementDriveRepository;
import com.examly.springapp.service.PlacementDriveService;

@Service
public class PlacementDriveServiceImpl implements PlacementDriveService {

    private final PlacementDriveRepository placementDriveRepository;

    public PlacementDriveServiceImpl(PlacementDriveRepository placementDriveRepository) {
        this.placementDriveRepository = placementDriveRepository;
    }

    @Override
    public List<PlacementDrive> getAllPlacementDrives() {
        return placementDriveRepository.findAll();
    }

    @Override
    public PlacementDrive getPlacementDriveById(Long id) {
        return placementDriveRepository.findById(id).orElse(null);
    }
}
