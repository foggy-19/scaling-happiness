package com.panonit.rereview.controllers;

import com.panonit.rereview.domain.dtos.ReviewCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.ReviewDto;
import com.panonit.rereview.domain.dtos.UserDto;
import com.panonit.rereview.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants/{restaurant_id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable("restaurant_id") String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ReviewDto review = service.createReview(getUserFromJwt(jwt), restaurantId, reviewCreateUpdateRequestDto);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{review_id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable("restaurant_id") String restaurantId,
            @PathVariable("review_id") String reviewId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ReviewDto review = service.updateReview(getUserFromJwt(jwt), restaurantId, reviewId, reviewCreateUpdateRequestDto);

        return ResponseEntity.ok(review);
    }

    @DeleteMapping(path = "/{review_id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("restaurant_id") String restaurantId,
            @PathVariable("review_id") String reviewId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        service.deleteReview(getUserFromJwt(jwt), restaurantId, reviewId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{review_id}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable("restaurant_id") String restaurantId,
            @PathVariable("review_id") String reviewId
    ) {
        return service.getReview(restaurantId, reviewId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDto>> getReviews(
            @PathVariable("restaurant_id") String restaurantId,
            @PageableDefault(size = 20, sort = "datePosted", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewDto> reviews = service.getReviews(restaurantId, pageable);

        return ResponseEntity.ok(reviews);
    }

    private UserDto getUserFromJwt(Jwt jwt) {
        return UserDto.builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .build();
    }
}
