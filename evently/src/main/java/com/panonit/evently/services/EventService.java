package com.panonit.evently.services;

import com.panonit.evently.domain.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {

    CreateEventResponseDto createEventForOrganizer(UUID organizerId, CreateEventRequestDto request);

    UpdateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto request);

    void deleteEventForOrganizer(UUID organizerId, UUID id);

    Page<EventDto> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<EventDto> getEventForOrganizer(UUID organizerId, UUID id);
}
