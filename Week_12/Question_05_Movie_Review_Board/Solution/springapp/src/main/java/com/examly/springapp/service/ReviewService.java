package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Review;

public interface ReviewService {

    List<Review> getAllReviews();

    Review addReview(Review review);

    boolean deleteReview(Long id);
}
