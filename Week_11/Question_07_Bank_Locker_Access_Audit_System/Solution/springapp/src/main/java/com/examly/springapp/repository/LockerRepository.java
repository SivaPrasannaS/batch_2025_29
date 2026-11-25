package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Locker;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {

    Optional<Locker> findByLockerNumber(String lockerNumber);

    boolean existsByLockerNumber(String lockerNumber);
}
