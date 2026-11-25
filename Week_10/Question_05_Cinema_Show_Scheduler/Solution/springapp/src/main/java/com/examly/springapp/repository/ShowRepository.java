package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByScreenNumber(Integer screenNumber);

    List<Show> findByAvailableSeatsGreaterThanEqual(Integer minSeats);
}
