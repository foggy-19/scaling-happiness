package com.panonit.rereview.services;

import com.panonit.rereview.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.RestaurantDto;
import com.panonit.rereview.domain.dtos.RestaurantSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestaurantService {

    RestaurantDto createRestaurant(RestaurantCreateUpdateRequestDto requestDto);

    RestaurantDto updateRestaurant(String id, RestaurantCreateUpdateRequestDto requestDto);

    Optional<RestaurantDto> getRestaurant(String id);

    void deleteRestaurant(String restaurantId);

    Page<RestaurantSummaryDto> searchRestaurants(String query, Float minRating, Float latitude, Float longitude, Float radius, Pageable pageable);

}
