package com.panonit.evently.controllers;

import com.panonit.evently.domain.TicketValidationMethod;
import com.panonit.evently.domain.dtos.TicketValidationRequestDto;
import com.panonit.evently.domain.dtos.TicketValidationResponseDto;
import com.panonit.evently.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService service;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(@RequestBody TicketValidationRequestDto requestDto) {
        TicketValidationResponseDto response;
        if (requestDto.getMethod() == TicketValidationMethod.QR_SCAN) {
            response = service.validateTicketByQrCode(requestDto.getId());
        } else {
            response = service.validateTicketManually(requestDto.getId());
        }

        return ResponseEntity.ok(response);
    }
}
