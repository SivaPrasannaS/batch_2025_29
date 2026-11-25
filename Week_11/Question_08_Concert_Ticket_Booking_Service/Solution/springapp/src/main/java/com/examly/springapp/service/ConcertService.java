package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ConcertRequest;
import com.examly.springapp.model.Concert;

public interface ConcertService {

    Concert createConcert(ConcertRequest request);

    List<Concert> getAllConcerts();

    Concert getConcertById(Long id);

    Concert bookSeats(Long id, Integer seats);
}
