package com.panonit.rereview.mappers;

import com.panonit.rereview.domain.dtos.GeoPointDto;
import com.panonit.rereview.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.RestaurantDto;
import com.panonit.rereview.domain.dtos.RestaurantSummaryDto;
import com.panonit.rereview.domain.entities.Restaurant;
import com.panonit.rereview.domain.entities.RestaurantCreateUpdateRequest;
import com.panonit.rereview.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantDto toDto(Restaurant restaurant);

    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto dto);

    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    RestaurantSummaryDto toSummaryDto(Restaurant restaurant);

    @Mapping(target = "latitude", expression = "java(geoPointDto.getLatitude())")
    @Mapping(target = "longitude", expression = "java(geoPointDto.getLongitude())")
    GeoPoint toGeoPointEntity(GeoPointDto geoPointDto);

    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);

    @Named("populateTotalReviews")
    default Integer populateTotalReviews(List<Review> reviews) {
        return reviews.size();
    }
}
