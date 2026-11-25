package com.examly.springapp.exception;

public class DuplicateTicketException extends RuntimeException {

    public DuplicateTicketException(String message) {
        super(message);
    }
}
