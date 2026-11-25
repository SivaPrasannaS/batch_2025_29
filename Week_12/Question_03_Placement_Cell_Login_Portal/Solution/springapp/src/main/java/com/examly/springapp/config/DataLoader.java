package com.examly.springapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.PlacementDrive;
import com.examly.springapp.repository.PlacementDriveRepository;

/** Adds sample data when the table is empty (provided; not part of the task). */
@Component
public class DataLoader implements CommandLineRunner {

    private final PlacementDriveRepository placementDriveRepository;

    public DataLoader(PlacementDriveRepository placementDriveRepository) {
        this.placementDriveRepository = placementDriveRepository;
    }

    @Override
    public void run(String... args) {
        if (placementDriveRepository.count() > 0) {
            return;
        }
        placementDriveRepository.save(create("Infosys", "Systems Engineer", 3.6));
        placementDriveRepository.save(create("TCS", "Assistant System Engineer", 3.4));
        placementDriveRepository.save(create("Zoho", "Member Technical Staff", 6.0));
    }

    private PlacementDrive create(String companyName, String jobRole, Double packageLpa) {
        PlacementDrive placementDrive = new PlacementDrive();
        placementDrive.setCompanyName(companyName);
        placementDrive.setJobRole(jobRole);
        placementDrive.setPackageLpa(packageLpa);
        return placementDrive;
    }
}
