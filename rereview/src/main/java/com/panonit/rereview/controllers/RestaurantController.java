package com.panonit.rereview.controllers;

import com.panonit.rereview.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.RestaurantDto;
import com.panonit.rereview.domain.dtos.RestaurantSummaryDto;
import com.panonit.rereview.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody RestaurantCreateUpdateRequestDto requestDto) {
        return new ResponseEntity<>(service.createRestaurant(requestDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{restaurant_id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable(value = "restaurant_id") String restaurantId,
            @Valid @RequestBody RestaurantCreateUpdateRequestDto requestDto
    ) {
        return new ResponseEntity<>(service.updateRestaurant(restaurantId, requestDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{restaurant_id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(value = "restaurant_id") String restaurantId) {
        service.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{restaurant_id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable(value = "restaurant_id") String restaurantId) {
        return service.getRestaurant(restaurantId)
                .map(restaurantDto -> new ResponseEntity<>(restaurantDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantSummaryDto>> searchRestaurants(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<RestaurantSummaryDto> summary = service.searchRestaurants(q, minRating, latitude, longitude, radius, pageable);

        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}
