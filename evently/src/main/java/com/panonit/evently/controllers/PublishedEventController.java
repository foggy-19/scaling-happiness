package com.panonit.evently.controllers;

import com.panonit.evently.domain.dtos.GetPublishedEventResponseDto;
import com.panonit.evently.domain.dtos.ListPublishedEventResponseDto;
import com.panonit.evently.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(name = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<ListPublishedEventResponseDto> page;
        if (query != null && !query.trim().isEmpty()) {
            page = service.searchPublishedEvents(query, pageable);
        } else {
            page = service.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetPublishedEventResponseDto> getPublishedEvent(
            @PathVariable UUID id
    ) {
        return service.getPublishedEvent(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
