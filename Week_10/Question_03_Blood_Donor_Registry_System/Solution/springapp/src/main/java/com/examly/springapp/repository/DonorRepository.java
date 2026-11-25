package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
}
