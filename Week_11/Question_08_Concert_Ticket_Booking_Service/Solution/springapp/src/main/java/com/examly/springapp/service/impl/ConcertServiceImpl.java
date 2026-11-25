package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ConcertRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.SeatsUnavailableException;
import com.examly.springapp.model.Concert;
import com.examly.springapp.repository.ConcertRepository;
import com.examly.springapp.service.ConcertService;

@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertServiceImpl(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Override
    public Concert createConcert(ConcertRequest request) {
        Concert concert = new Concert();
        concert.setTitle(request.getTitle());
        concert.setVenue(request.getVenue());
        concert.setTotalSeats(request.getTotalSeats());
        concert.setAvailableSeats(request.getTotalSeats());
        concert.setTicketPrice(request.getTicketPrice());
        return concertRepository.save(concert);
    }

    @Override
    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    @Override
    public Concert getConcertById(Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concert not found with id: " + id));
    }

    @Override
    public Concert bookSeats(Long id, Integer seats) {
        Concert concert = getConcertById(id);
        if (seats == null || seats < 1) {
            throw new SeatsUnavailableException("At least 1 seat must be booked");
        }
        if (seats > concert.getAvailableSeats()) {
            throw new SeatsUnavailableException("Only " + concert.getAvailableSeats() + " seats available for "
                    + concert.getTitle());
        }
        concert.setAvailableSeats(concert.getAvailableSeats() - seats);
        return concertRepository.save(concert);
    }
}
