package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ShowRequest;
import com.examly.springapp.model.Show;

public interface ShowService {

    Show createShow(ShowRequest request);

    Show getShowById(Long id);

    List<Show> getShowsByScreen(Integer screenNumber);

    List<Show> getAvailableShows(Integer minSeats);

    void deleteShow(Long id);
}
