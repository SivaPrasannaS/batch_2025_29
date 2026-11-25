package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ShowRequest;
import com.examly.springapp.model.Show;
import com.examly.springapp.service.ShowService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/shows")
@Validated
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<Show> createShow(@Valid @RequestBody ShowRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.createShow(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(
            @PathVariable @Min(value = 1, message = "Show id must be greater than 0") Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/screen/{screenNumber}")
    public ResponseEntity<List<Show>> getShowsByScreen(
            @PathVariable @Min(value = 1, message = "Screen number must be between 1 and 10")
            @Max(value = 10, message = "Screen number must be between 1 and 10") Integer screenNumber) {
        return ResponseEntity.ok(showService.getShowsByScreen(screenNumber));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Show>> getAvailableShows(
            @RequestParam @Min(value = 1, message = "Minimum seats must be at least 1")
            @Max(value = 300, message = "Minimum seats must not exceed 300") Integer minSeats) {
        return ResponseEntity.ok(showService.getAvailableShows(minSeats));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(
            @PathVariable @Min(value = 1, message = "Show id must be greater than 0") Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}
