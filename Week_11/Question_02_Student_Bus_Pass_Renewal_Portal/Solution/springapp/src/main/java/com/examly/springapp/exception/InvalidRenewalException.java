package com.examly.springapp.exception;

public class InvalidRenewalException extends RuntimeException {

    public InvalidRenewalException(String message) {
        super(message);
    }
}
