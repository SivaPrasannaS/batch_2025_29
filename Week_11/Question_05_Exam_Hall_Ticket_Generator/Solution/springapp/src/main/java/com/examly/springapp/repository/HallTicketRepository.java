package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.HallTicket;

@Repository
public interface HallTicketRepository extends JpaRepository<HallTicket, Long> {

    boolean existsByRollNumber(String rollNumber);

    Optional<HallTicket> findByTicketNumber(String ticketNumber);
}
