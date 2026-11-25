package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
