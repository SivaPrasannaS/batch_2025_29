package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.PlacementDrive;

public interface PlacementDriveService {

    List<PlacementDrive> getAllPlacementDrives();

    PlacementDrive getPlacementDriveById(Long id);
}
