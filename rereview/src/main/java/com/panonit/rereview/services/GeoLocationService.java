package com.panonit.rereview.services;

import com.panonit.rereview.domain.dtos.AddressDto;
import com.panonit.rereview.domain.dtos.GeoPointDto;

public interface GeoLocationService {
    GeoPointDto getGeoLocationFromAddress(AddressDto address);
}
