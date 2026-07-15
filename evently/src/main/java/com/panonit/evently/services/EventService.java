package com.panonit.evently.services;

import com.panonit.evently.domain.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {

    CreateEventResponseDto createEventForOrganizer(UUID organizerId, CreateEventRequestDto request);

    UpdateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto request);

    Page<ListEventResponseDto> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<GetEventResponseDto> getEventForOrganizer(UUID organizerId, UUID id);

    void deleteEventForOrganizer(UUID organizerId, UUID id);

    Page<ListPublishedEventResponseDto> listPublishedEvents(Pageable pageable);

    Page<ListPublishedEventResponseDto> searchPublishedEvents(String query, Pageable pageable);
}
