package com.examly.springapp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.BookingRequest;
import com.examly.springapp.exception.BusinessRuleException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Flight;
import com.examly.springapp.repository.BookingRepository;
import com.examly.springapp.repository.FlightRepository;
import com.examly.springapp.service.BookingService;
import com.examly.springapp.service.FlightService;
import com.examly.springapp.utils.BookingReferenceGenerator;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository,
            FlightService flightService) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }

    @Override
    @Transactional
    public Booking bookSeats(BookingRequest request) {
        Flight flight = flightService.getFlightById(request.getFlightId());
        if (request.getSeatsBooked() > flight.getAvailableSeats()) {
            throw new BusinessRuleException("Only " + flight.getAvailableSeats() + " seats available on flight "
                    + flight.getFlightNumber());
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - request.getSeatsBooked());
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setBookingReference(BookingReferenceGenerator.generate());
        booking.setPassengerName(request.getPassengerName());
        booking.setPassengerEmail(request.getPassengerEmail());
        booking.setSeatsBooked(request.getSeatsBooked());
        booking.setTotalFare(flight.getFare() * request.getSeatsBooked());
        booking.setFlight(flight);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getSeatsBooked());
        flightRepository.save(flight);
        bookingRepository.delete(booking);
    }
}
