package com.examly.springapp.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.HallTicketRequest;
import com.examly.springapp.model.HallTicket;
import com.examly.springapp.service.HallTicketService;

@RestController
@RequestMapping("/api/hall-tickets")
public class HallTicketController {

    private final HallTicketService ticketService;

    public HallTicketController(HallTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<HallTicket> generateTicket(@RequestBody HallTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.generateTicket(request));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countTickets() {
        return ResponseEntity.ok(Map.of("count", ticketService.countTickets()));
    }

    @GetMapping("/{ticketNumber}")
    public ResponseEntity<HallTicket> getTicketByNumber(@PathVariable String ticketNumber) {
        return ResponseEntity.ok(ticketService.getTicketByNumber(ticketNumber));
    }
}
