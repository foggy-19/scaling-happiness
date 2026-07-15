package com.panonit.evently.controllers;

import com.panonit.evently.domain.dtos.ListPublishedEventResponseDto;
import com.panonit.evently.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
