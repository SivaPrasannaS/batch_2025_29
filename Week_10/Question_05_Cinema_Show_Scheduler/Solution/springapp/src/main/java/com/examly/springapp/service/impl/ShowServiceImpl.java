package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.ShowRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Show;
import com.examly.springapp.repository.ShowRepository;
import com.examly.springapp.service.ShowService;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public Show createShow(ShowRequest request) {
        Show show = new Show();
        show.setMovieTitle(request.getMovieTitle());
        show.setScreenNumber(request.getScreenNumber());
        show.setShowTime(request.getShowTime());
        show.setTicketPrice(request.getTicketPrice());
        show.setAvailableSeats(request.getAvailableSeats());
        return showRepository.save(show);
    }

    @Override
    public Show getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));
    }

    @Override
    public List<Show> getShowsByScreen(Integer screenNumber) {
        return showRepository.findByScreenNumber(screenNumber);
    }

    @Override
    public List<Show> getAvailableShows(Integer minSeats) {
        return showRepository.findByAvailableSeatsGreaterThanEqual(minSeats);
    }

    @Override
    public void deleteShow(Long id) {
        showRepository.delete(getShowById(id));
    }
}
