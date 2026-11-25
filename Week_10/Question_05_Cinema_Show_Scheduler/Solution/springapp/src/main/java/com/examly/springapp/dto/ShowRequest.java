package com.examly.springapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShowRequest {

    @NotBlank(message = "Movie title is required")
    @Size(max = 60, message = "Movie title must not exceed 60 characters")
    private String movieTitle;

    @NotNull(message = "Screen number is required")
    @Min(value = 1, message = "Screen number must be between 1 and 10")
    @Max(value = 10, message = "Screen number must be between 1 and 10")
    private Integer screenNumber;

    @NotBlank(message = "Show time is required")
    @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "Show time must be in HH:mm 24-hour format")
    private String showTime;

    @NotNull(message = "Ticket price is required")
    @Min(value = 100, message = "Ticket price must be at least 100")
    private Double ticketPrice;

    @NotNull(message = "Available seats is required")
    @Min(value = 0, message = "Available seats cannot be negative")
    @Max(value = 300, message = "Available seats must not exceed 300")
    private Integer availableSeats;

    public ShowRequest() {
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Integer getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(Integer screenNumber) {
        this.screenNumber = screenNumber;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
}
