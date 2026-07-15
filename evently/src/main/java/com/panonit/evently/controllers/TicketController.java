package com.panonit.evently.controllers;

import com.panonit.evently.domain.dtos.GetTicketResponseDto;
import com.panonit.evently.domain.dtos.ListTicketResponseDto;
import com.panonit.evently.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.panonit.evently.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        return ResponseEntity.ok(service.listTicketsForUser(parseUserId(jwt), pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetTicketResponseDto> getTicket(@AuthenticationPrincipal Jwt jwt, @PathVariable(name = "id") UUID ticketId) {
        return service.getTicketForUser(parseUserId(jwt), ticketId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
