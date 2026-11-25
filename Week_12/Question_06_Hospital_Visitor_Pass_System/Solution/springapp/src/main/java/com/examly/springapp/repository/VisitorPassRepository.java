package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.VisitorPass;

@Repository
public interface VisitorPassRepository extends JpaRepository<VisitorPass, Long> {
}
