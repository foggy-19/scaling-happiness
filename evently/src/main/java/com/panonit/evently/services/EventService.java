package com.panonit.evently.services;

import com.panonit.evently.domain.dtos.CreateEventRequestDto;
import com.panonit.evently.domain.dtos.CreateEventResponseDto;
import com.panonit.evently.domain.dtos.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {

    CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto request);

    Page<EventDto> listEventsForOrganizer(UUID organizerId, Pageable pageable);
}
