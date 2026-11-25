package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.PlacementDrive;
import com.examly.springapp.service.PlacementDriveService;

@RestController
@RequestMapping("/api/drives")
public class PlacementDriveController {

    private final PlacementDriveService placementDriveService;

    public PlacementDriveController(PlacementDriveService placementDriveService) {
        this.placementDriveService = placementDriveService;
    }

    @GetMapping
    public ResponseEntity<List<PlacementDrive>> getAllPlacementDrives() {
        return ResponseEntity.ok(placementDriveService.getAllPlacementDrives());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacementDrive> getPlacementDriveById(@PathVariable Long id) {
        PlacementDrive placementDrive = placementDriveService.getPlacementDriveById(id);
        if (placementDrive == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(placementDrive);
    }
}
