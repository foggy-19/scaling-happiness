package com.panonit.rereview.services.impl;

import com.panonit.rereview.domain.dtos.ReviewCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.ReviewDto;
import com.panonit.rereview.domain.dtos.UserDto;
import com.panonit.rereview.domain.entities.Photo;
import com.panonit.rereview.domain.entities.Restaurant;
import com.panonit.rereview.domain.entities.Review;
import com.panonit.rereview.domain.entities.ReviewCreateUpdateRequest;
import com.panonit.rereview.exceptions.RestaurantNotFoundException;
import com.panonit.rereview.exceptions.ReviewNotAllowedException;
import com.panonit.rereview.exceptions.ReviewNotFoundException;
import com.panonit.rereview.mappers.ReviewMapper;
import com.panonit.rereview.mappers.UserMapper;
import com.panonit.rereview.repositories.RestaurantRepository;
import com.panonit.rereview.services.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @Override
    public ReviewDto createReview(UserDto author, String restaurantId, ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto) {
        log.info("createReview");

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toEntity(reviewCreateUpdateRequestDto);

        boolean hasReviewed = restaurant.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));
        if (hasReviewed) {
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }

        String reviewId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        List<Photo> photos = reviewCreateUpdateRequest.getPhotoIds().stream()
                .map(url -> Photo.builder().url(url).uploadDate(now).build())
                .toList();

        Review review = Review.builder()
                .id(reviewId)
                .content(reviewCreateUpdateRequest.getContent())
                .rating(reviewCreateUpdateRequest.getRating())
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(userMapper.toEntity(author))
                .photos(photos)
                .build();

        restaurant.getReviews().add(review);
        updateAverageRating(restaurant);

        restaurant = restaurantRepository.save(restaurant);
        review = getReviewFromRestaurant(restaurant, reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found after saving"));

        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto updateReview(UserDto author, String restaurantId, String reviewId, ReviewCreateUpdateRequestDto reviewCreateUpdateRequestDto) {
        log.info("updateReview");

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        Review review = getReviewFromRestaurant(restaurant, reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found"));

        if (!review.getWrittenBy().getId().equals(author.getId())) {
            throw new ReviewNotAllowedException("User is not the owner of this review");
        }

        LocalDateTime now = LocalDateTime.now();

        if (review.getLastEdited().isBefore(now.minusDays(2))) {
            throw new ReviewNotAllowedException("Review can only be updated within 48 hours of posting");
        }

        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toEntity(reviewCreateUpdateRequestDto);

        List<Photo> photos = reviewCreateUpdateRequest.getPhotoIds().stream()
                .map(url -> Photo.builder().url(url).uploadDate(now).build())
                .toList();

        review.setContent(reviewCreateUpdateRequest.getContent());
        review.setRating(reviewCreateUpdateRequest.getRating());
        review.setLastEdited(now);
        review.setPhotos(photos);

        restaurant.getReviews().removeIf(r -> r.getId().equals(reviewId));
        restaurant.getReviews().add(review);

        updateAverageRating(restaurant);

        restaurant = restaurantRepository.save(restaurant);
        review = getReviewFromRestaurant(restaurant, reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found after saving"));

        return reviewMapper.toDto(review);
    }

    @Override
    public void deleteReview(UserDto author, String restaurantId, String reviewId) {
        log.info("deleteReview");

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        Review review = getReviewFromRestaurant(restaurant, reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found"));

        if (!review.getWrittenBy().getId().equals(author.getId())) {
            throw new ReviewNotAllowedException("User is not the owner of this review");
        }

        restaurant.getReviews().removeIf(r -> r.getId().equals(reviewId));
        updateAverageRating(restaurant);

        restaurantRepository.save(restaurant);
    }

    @Override
    public Optional<ReviewDto> getReview(String restaurantId, String reviewId) {
        log.info("getReview");

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        return getReviewFromRestaurant(restaurant, reviewId).map(reviewMapper::toDto);
    }

    @Override
    public Page<ReviewDto> getReviews(String restaurantId, Pageable pageable) {
        log.info("getReviews");

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));

        List<Review> reviews = restaurant.getReviews();

        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            boolean isAscending = order.isAscending();

            Comparator<Review> comparator = switch (property) {
                case "rating" -> Comparator.comparing(Review::getRating);
                default -> Comparator.comparing(Review::getDatePosted);
            };

            reviews.sort(isAscending ? comparator : comparator.reversed());
        } else {
            reviews.sort(Comparator.comparing(Review::getDatePosted));
        }

        return new PageImpl<>(reviews.stream().map(reviewMapper::toDto).toList(), pageable, reviews.size());
    }

    private Optional<Review> getReviewFromRestaurant(Restaurant restaurant, String reviewId) {
        return restaurant.getReviews().stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst();
    }

    private void updateAverageRating(Restaurant restaurant) {
        List<Review> reviews = restaurant.getReviews();
        float rating;
        if (reviews.isEmpty()) {
            rating = 0f;
        } else {
            rating = (float) reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0f);
        }

        restaurant.setAverageRating(rating);
    }
}
