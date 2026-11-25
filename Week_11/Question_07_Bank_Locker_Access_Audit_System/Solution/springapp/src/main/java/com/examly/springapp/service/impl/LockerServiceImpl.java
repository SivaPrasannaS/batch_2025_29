package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.LockerRequest;
import com.examly.springapp.exception.DuplicateLockerException;
import com.examly.springapp.exception.LockerStateException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Locker;
import com.examly.springapp.repository.LockerRepository;
import com.examly.springapp.service.LockerService;

@Service
public class LockerServiceImpl implements LockerService {

    private final LockerRepository lockerRepository;

    public LockerServiceImpl(LockerRepository lockerRepository) {
        this.lockerRepository = lockerRepository;
    }

    @Override
    public Locker allocateLocker(LockerRequest request) {
        if (lockerRepository.existsByLockerNumber(request.getLockerNumber())) {
            throw new DuplicateLockerException("Locker " + request.getLockerNumber() + " is already allocated");
        }
        Locker locker = new Locker();
        locker.setLockerNumber(request.getLockerNumber());
        locker.setCustomerName(request.getCustomerName());
        locker.setStatus("CLOSED");
        locker.setAccessCount(0);
        return lockerRepository.save(locker);
    }

    @Override
    public Locker getLockerDetails(String lockerNumber) {
        return lockerRepository.findByLockerNumber(lockerNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Locker not found: " + lockerNumber));
    }

    @Override
    public List<Locker> getAllLockers() {
        return lockerRepository.findAll();
    }

    @Override
    public Locker openLocker(String lockerNumber) {
        Locker locker = getLockerDetails(lockerNumber);
        if ("OPEN".equals(locker.getStatus())) {
            throw new LockerStateException("Locker " + lockerNumber + " is already open");
        }
        locker.setStatus("OPEN");
        locker.setAccessCount(locker.getAccessCount() + 1);
        return lockerRepository.save(locker);
    }

    @Override
    public Locker closeLocker(String lockerNumber) {
        Locker locker = getLockerDetails(lockerNumber);
        if ("CLOSED".equals(locker.getStatus())) {
            throw new LockerStateException("Locker " + lockerNumber + " is already closed");
        }
        locker.setStatus("CLOSED");
        return lockerRepository.save(locker);
    }
}
