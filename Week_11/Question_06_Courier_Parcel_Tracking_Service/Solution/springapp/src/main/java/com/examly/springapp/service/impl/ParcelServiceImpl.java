package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ParcelRequest;
import com.examly.springapp.exception.InvalidStatusException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Parcel;
import com.examly.springapp.repository.ParcelRepository;
import com.examly.springapp.service.ParcelService;

@Service
public class ParcelServiceImpl implements ParcelService {

    private static final List<String> STATUSES = List.of("BOOKED", "IN_TRANSIT", "DELIVERED");

    private final ParcelRepository parcelRepository;

    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public Parcel bookParcel(ParcelRequest request) {
        Parcel parcel = new Parcel();
        parcel.setSenderName(request.getSenderName());
        parcel.setReceiverCity(request.getReceiverCity());
        parcel.setWeightKg(request.getWeightKg());
        parcel.setStatus("BOOKED");
        Parcel saved = parcelRepository.save(parcel);
        saved.setTrackingNumber(String.format("PCL%06d", saved.getId()));
        return parcelRepository.save(saved);
    }

    @Override
    public Parcel trackParcel(String trackingNumber) {
        return parcelRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel not found: " + trackingNumber));
    }

    @Override
    public Parcel updateStatus(String trackingNumber, String status) {
        if (!STATUSES.contains(status)) {
            throw new InvalidStatusException("Invalid parcel status: " + status);
        }
        Parcel parcel = trackParcel(trackingNumber);
        parcel.setStatus(status);
        return parcelRepository.save(parcel);
    }
}
