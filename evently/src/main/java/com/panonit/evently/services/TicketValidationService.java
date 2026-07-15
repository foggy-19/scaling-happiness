package com.panonit.evently.services;

import com.panonit.evently.domain.dtos.TicketValidationResponseDto;

import java.util.UUID;

public interface TicketValidationService {

    TicketValidationResponseDto validateTicketByQrCode(UUID qrcodeId);

    TicketValidationResponseDto validateTicketManually(UUID ticketId);
}
