package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.LockerRequest;
import com.examly.springapp.model.Locker;
import com.examly.springapp.service.LockerService;

@RestController
@RequestMapping("/api/lockers")
public class LockerController {

    private final LockerService lockerService;

    public LockerController(LockerService lockerService) {
        this.lockerService = lockerService;
    }

    @PostMapping
    public ResponseEntity<Locker> allocateLocker(@RequestBody LockerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lockerService.allocateLocker(request));
    }

    @GetMapping
    public ResponseEntity<List<Locker>> getAllLockers() {
        return ResponseEntity.ok(lockerService.getAllLockers());
    }

    @GetMapping("/{lockerNumber}")
    public ResponseEntity<Locker> getLockerDetails(@PathVariable String lockerNumber) {
        return ResponseEntity.ok(lockerService.getLockerDetails(lockerNumber));
    }

    @PostMapping("/{lockerNumber}/open")
    public ResponseEntity<Locker> openLocker(@PathVariable String lockerNumber) {
        return ResponseEntity.ok(lockerService.openLocker(lockerNumber));
    }

    @PostMapping("/{lockerNumber}/close")
    public ResponseEntity<Locker> closeLocker(@PathVariable String lockerNumber) {
        return ResponseEntity.ok(lockerService.closeLocker(lockerNumber));
    }
}
