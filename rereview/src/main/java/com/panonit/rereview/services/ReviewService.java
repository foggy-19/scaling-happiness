package com.panonit.rereview.services;

import com.panonit.rereview.domain.dtos.ReviewCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.ReviewDto;
import com.panonit.rereview.domain.dtos.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {

    ReviewDto createReview(UserDto author, String restaurantId, ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto);

    ReviewDto updateReview(UserDto author, String restaurantId, String reviewId, ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto);

    void deleteReview(UserDto author, String restaurantId, String reviewId);

    Optional<ReviewDto> getReview(String restaurantId, String reviewId);

    Page<ReviewDto> getReviews(String restaurantId, Pageable pageable);
}
