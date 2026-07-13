package com.panonit.rereview.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {

    private String name;

    private String cuisineType;

    private String contactInformation;

    private Address address;

    private OperatingHours operatingHours;

    private List<String> photoIds = new ArrayList<>();
}
