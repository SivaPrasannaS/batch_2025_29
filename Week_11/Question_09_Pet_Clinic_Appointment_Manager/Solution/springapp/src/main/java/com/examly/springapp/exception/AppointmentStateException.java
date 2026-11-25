package com.examly.springapp.exception;

public class AppointmentStateException extends RuntimeException {

    public AppointmentStateException(String message) {
        super(message);
    }
}
