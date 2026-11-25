package com.examly.springapp.service.impl;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.HallTicketRequest;
import com.examly.springapp.exception.DuplicateTicketException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.HallTicket;
import com.examly.springapp.repository.HallTicketRepository;
import com.examly.springapp.service.HallTicketService;

@Service
public class HallTicketServiceImpl implements HallTicketService {

    private final HallTicketRepository ticketRepository;

    public HallTicketServiceImpl(HallTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public HallTicket generateTicket(HallTicketRequest request) {
        if (ticketRepository.existsByRollNumber(request.getRollNumber())) {
            throw new DuplicateTicketException("Hall ticket already generated for roll number " + request.getRollNumber());
        }
        HallTicket ticket = new HallTicket();
        ticket.setRollNumber(request.getRollNumber());
        ticket.setStudentName(request.getStudentName());
        ticket.setExamName(request.getExamName());
        ticket.setExamCenter(request.getExamCenter());
        ticket.setTicketNumber("HT-" + request.getRollNumber());
        return ticketRepository.save(ticket);
    }

    @Override
    public HallTicket getTicketByNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Hall ticket not found: " + ticketNumber));
    }

    @Override
    public long countTickets() {
        return ticketRepository.count();
    }
}
