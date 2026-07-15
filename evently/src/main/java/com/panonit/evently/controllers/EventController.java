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

import java.util.UUID;

import static com.panonit.evently.util.JwtUtil.parseUserId;

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
        return new ResponseEntity<>(service.createEventForOrganizer(parseUserId(jwt), request), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateEventRequestDto request
    ) {
        return ResponseEntity.ok(service.updateEventForOrganizer(parseUserId(jwt), id, request));
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.listEventsForOrganizer(parseUserId(jwt), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetEventResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        return service.getEventForOrganizer(parseUserId(jwt), id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        service.deleteEventForOrganizer(parseUserId(jwt), id);

        return ResponseEntity.noContent().build();
    }
}
