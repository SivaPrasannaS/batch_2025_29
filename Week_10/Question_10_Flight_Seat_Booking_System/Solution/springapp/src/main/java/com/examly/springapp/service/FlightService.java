package com.examly.springapp.service;

import com.examly.springapp.dto.FlightRequest;
import com.examly.springapp.model.Flight;

public interface FlightService {

    Flight addFlight(FlightRequest request);

    Flight getFlightById(Long id);
}
