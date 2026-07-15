package com.panonit.evently.controllers;

import com.panonit.evently.domain.dtos.*;
import com.panonit.evently.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto request
    ) {
        return new ResponseEntity<>(service.createEventForOrganizer(getUserId(jwt), request), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateEventRequestDto request
    ) {
        return new ResponseEntity<>(service.updateEventForOrganizer(getUserId(jwt), id, request), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        service.deleteEventForOrganizer(getUserId(jwt), id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<EventDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        return new ResponseEntity<>(service.listEventsForOrganizer(getUserId(jwt), pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        return service.getEventForOrganizer(getUserId(jwt), id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private UUID getUserId(Jwt jwt) {
        return UUID.fromString(Objects.requireNonNull(jwt.getSubject()));
    }
}
