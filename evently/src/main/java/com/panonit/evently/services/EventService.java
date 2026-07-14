package com.panonit.evently.services;

import com.panonit.evently.domain.dtos.CreateEventRequestDto;
import com.panonit.evently.domain.dtos.CreateEventResponseDto;

import java.util.UUID;

public interface EventService {

    CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto request);
}
