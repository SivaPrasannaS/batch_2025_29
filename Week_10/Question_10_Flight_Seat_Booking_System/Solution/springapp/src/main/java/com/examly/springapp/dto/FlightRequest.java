package com.examly.springapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{3,4}$", message = "Flight number must be 2 uppercase letters followed by 3 or 4 digits")
    private String flightNumber;

    @NotBlank(message = "Origin is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Origin must be a 3-letter airport code")
    private String origin;

    @NotBlank(message = "Destination is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Destination must be a 3-letter airport code")
    private String destination;

    @NotNull(message = "Total seats is required")
    @Min(value = 10, message = "Total seats must be at least 10")
    @Max(value = 400, message = "Total seats must not exceed 400")
    private Integer totalSeats;

    @NotNull(message = "Fare is required")
    @Min(value = 500, message = "Fare must be at least 500")
    private Double fare;

    public FlightRequest() {
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }
}
