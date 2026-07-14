package com.panonit.evently.controllers;

import com.panonit.evently.domain.dtos.CreateEventRequestDto;
import com.panonit.evently.domain.dtos.CreateEventResponseDto;
import com.panonit.evently.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        UUID organizerId = UUID.fromString(Objects.requireNonNull(jwt.getSubject()));

        return new ResponseEntity<>(service.createEvent(organizerId, request), HttpStatus.CREATED);
    }
}
