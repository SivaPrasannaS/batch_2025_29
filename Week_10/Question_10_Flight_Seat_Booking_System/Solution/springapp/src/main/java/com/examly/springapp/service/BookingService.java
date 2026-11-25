package com.examly.springapp.service;

import com.examly.springapp.dto.BookingRequest;
import com.examly.springapp.model.Booking;

public interface BookingService {

    Booking bookSeats(BookingRequest request);

    Booking getBookingById(Long id);

    void cancelBooking(Long id);
}
