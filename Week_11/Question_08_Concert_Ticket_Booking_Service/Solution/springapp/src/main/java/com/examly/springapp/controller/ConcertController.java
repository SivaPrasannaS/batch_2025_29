package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ConcertRequest;
import com.examly.springapp.model.Concert;
import com.examly.springapp.service.ConcertService;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestBody ConcertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(concertService.createConcert(request));
    }

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        return ResponseEntity.ok(concertService.getAllConcerts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        return ResponseEntity.ok(concertService.getConcertById(id));
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<Concert> bookSeats(@PathVariable Long id, @RequestParam Integer seats) {
        return ResponseEntity.ok(concertService.bookSeats(id, seats));
    }
}
