package com.panonit.rereview.services.impl;

import com.panonit.rereview.domain.dtos.GeoPointDto;
import com.panonit.rereview.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.RestaurantDto;
import com.panonit.rereview.domain.dtos.RestaurantSummaryDto;
import com.panonit.rereview.domain.entities.Photo;
import com.panonit.rereview.domain.entities.Restaurant;
import com.panonit.rereview.domain.entities.RestaurantCreateUpdateRequest;
import com.panonit.rereview.exceptions.RestaurantNotFoundException;
import com.panonit.rereview.mappers.RestaurantMapper;
import com.panonit.rereview.repositories.RestaurantRepository;
import com.panonit.rereview.services.GeoLocationService;
import com.panonit.rereview.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper mapper;
    private final RestaurantRepository repository;

    private final GeoLocationService locationService;

    @Override
    public RestaurantDto createRestaurant(RestaurantCreateUpdateRequestDto requestDto) {
        log.info("createRestaurant");

        GeoPointDto geoLocation = locationService.getGeoLocationFromAddress(requestDto.getAddress());

        RestaurantCreateUpdateRequest request = mapper.toRestaurantCreateUpdateRequest(requestDto);

        List<Photo> photos = request.getPhotoIds().stream()
                .map(url -> Photo.builder().url(url).uploadDate(LocalDateTime.now()).build())
                .toList();

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInformation(request.getContactInformation())
                .averageRating(0f)
                .geoLocation(mapper.toGeoPointEntity(geoLocation))
                .address(request.getAddress())
                .operatingHours(request.getOperatingHours())
                .photos(photos)
                .build();

        return mapper.toDto(repository.save(restaurant));
    }

    @Override
    public RestaurantDto updateRestaurant(String id, RestaurantCreateUpdateRequestDto requestDto) {
        log.info("updateRestaurant");

        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));

        GeoPointDto geoLocation = locationService.getGeoLocationFromAddress(requestDto.getAddress());
        RestaurantCreateUpdateRequest request = mapper.toRestaurantCreateUpdateRequest(requestDto);
        List<Photo> photos = request.getPhotoIds().stream()
                .map(url -> Photo.builder().url(url).uploadDate(LocalDateTime.now()).build())
                .toList();

        restaurant.setName(request.getName());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setContactInformation(request.getContactInformation());
        restaurant.setGeoLocation(mapper.toGeoPointEntity(geoLocation));
        restaurant.setAddress(request.getAddress());
        restaurant.setOperatingHours(request.getOperatingHours());
        restaurant.setPhotos(photos);

        return mapper.toDto(repository.save(restaurant));
    }

    @Override
    public Optional<RestaurantDto> getRestaurant(String id) {
        log.info("getRestaurant");

        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public void deleteRestaurant(String restaurantId) {
        log.info("deleteRestaurant");

        if (!repository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found");
        }

        repository.deleteById(restaurantId);
    }

    @Override
    public Page<RestaurantSummaryDto> searchRestaurants(String query, Float minRating, Float latitude, Float longitude, Float radius, Pageable pageable) {
        log.info("searchRestaurants");

        if (minRating != null && (query == null || query.isEmpty())) {
            return repository.findByAverageRatingGreaterThanEqual(minRating, pageable).map(mapper::toSummaryDto);
        }

        if (query != null && !query.trim().isEmpty()) {
            float rating = minRating == null ? 0f : minRating;
            return repository.findByQueryAndMinRating(query, rating, pageable).map(mapper::toSummaryDto);
        }

        if (latitude != null && longitude != null && radius != null) {
            return repository.findByLocationNear(latitude, longitude, radius, pageable).map(mapper::toSummaryDto);
        }

        return repository.findAll(pageable).map(mapper::toSummaryDto);
    }
}
