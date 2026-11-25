package com.examly.springapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookingRequest {

    @NotNull(message = "Flight id is required")
    private Long flightId;

    @NotBlank(message = "Passenger name is required")
    @Size(min = 3, max = 50, message = "Passenger name must be between 3 and 50 characters")
    private String passengerName;

    @NotBlank(message = "Passenger email is required")
    @Email(message = "Passenger email must be a valid email address")
    private String passengerEmail;

    @NotNull(message = "Seats booked is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    @Max(value = 6, message = "A maximum of 6 seats can be booked at a time")
    private Integer seatsBooked;

    public BookingRequest() {
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }
}
