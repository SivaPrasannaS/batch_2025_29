package com.examly.springapp.service.impl;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.FlightRequest;
import com.examly.springapp.exception.BusinessRuleException;
import com.examly.springapp.exception.DuplicateResourceException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Flight;
import com.examly.springapp.repository.FlightRepository;
import com.examly.springapp.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight addFlight(FlightRequest request) {
        if (request.getOrigin().equals(request.getDestination())) {
            throw new BusinessRuleException("Origin and destination cannot be the same");
        }
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new DuplicateResourceException(
                    "Flight already exists with flight number: " + request.getFlightNumber());
        }
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setOrigin(request.getOrigin());
        flight.setDestination(request.getDestination());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setAvailableSeats(request.getTotalSeats());
        flight.setFare(request.getFare());
        return flightRepository.save(flight);
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }
}
