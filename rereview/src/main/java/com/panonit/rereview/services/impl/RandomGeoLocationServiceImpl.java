package com.panonit.rereview.services.impl;

import com.panonit.rereview.domain.dtos.AddressDto;
import com.panonit.rereview.domain.dtos.GeoPointDto;
import com.panonit.rereview.services.GeoLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class RandomGeoLocationServiceImpl implements GeoLocationService {

    private static final float MIN_LATITUDE = 51.28f;
    private static final float MAX_LATITUDE = 51.686f;

    private static final float MIN_LONGITUDE = -0.489f;
    private static final float MAX_LONGITUDE = 0.236f;

    @Override
    public GeoPointDto getGeoLocationFromAddress(AddressDto address) {
        log.info("getGeoLocationFromAddress");

        Random random = new Random();
        double latitude = MIN_LATITUDE + random.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE);
        double longitude = MIN_LONGITUDE + random.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE);

        return new GeoPointDto(longitude, latitude);
    }
}
