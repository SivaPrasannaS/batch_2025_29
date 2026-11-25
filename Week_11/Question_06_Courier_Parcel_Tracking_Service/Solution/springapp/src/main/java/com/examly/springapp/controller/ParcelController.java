package com.examly.springapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ParcelRequest;
import com.examly.springapp.model.Parcel;
import com.examly.springapp.service.ParcelService;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping
    public ResponseEntity<Parcel> bookParcel(@RequestBody ParcelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parcelService.bookParcel(request));
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<Parcel> trackParcel(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(parcelService.trackParcel(trackingNumber));
    }

    @PatchMapping("/{trackingNumber}/status")
    public ResponseEntity<Parcel> updateStatus(@PathVariable String trackingNumber, @RequestParam String value) {
        return ResponseEntity.ok(parcelService.updateStatus(trackingNumber, value));
    }
}
