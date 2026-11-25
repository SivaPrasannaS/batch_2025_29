package com.examly.springapp.exception;

public class DuplicateLockerException extends RuntimeException {

    public DuplicateLockerException(String message) {
        super(message);
    }
}
