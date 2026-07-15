package com.panonit.evently.controllers;

import com.panonit.evently.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.panonit.evently.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/events/{eventId}/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeService service;

    @PostMapping(path = "/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("ticketTypeId") UUID ticketTypeId
    ) {
        service.purchaseTicket(parseUserId(jwt), ticketTypeId);

        return ResponseEntity.noContent().build();
    }
}
