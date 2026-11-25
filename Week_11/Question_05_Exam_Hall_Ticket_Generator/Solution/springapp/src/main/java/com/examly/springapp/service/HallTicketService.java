package com.examly.springapp.service;

import com.examly.springapp.dto.HallTicketRequest;
import com.examly.springapp.model.HallTicket;

public interface HallTicketService {

    HallTicket generateTicket(HallTicketRequest request);

    HallTicket getTicketByNumber(String ticketNumber);

    long countTickets();
}
