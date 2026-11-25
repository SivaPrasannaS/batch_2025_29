package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.PlacementDrive;

@Repository
public interface PlacementDriveRepository extends JpaRepository<PlacementDrive, Long> {
}
