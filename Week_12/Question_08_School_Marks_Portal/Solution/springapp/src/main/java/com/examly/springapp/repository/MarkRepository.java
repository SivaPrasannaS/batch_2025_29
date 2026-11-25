package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
}
