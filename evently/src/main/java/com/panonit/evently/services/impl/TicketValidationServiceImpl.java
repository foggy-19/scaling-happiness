package com.panonit.evently.services.impl;

import com.panonit.evently.domain.QrCodeStatus;
import com.panonit.evently.domain.TicketValidationMethod;
import com.panonit.evently.domain.TicketValidationStatus;
import com.panonit.evently.domain.dtos.TicketValidationResponseDto;
import com.panonit.evently.domain.entities.QrCode;
import com.panonit.evently.domain.entities.Ticket;
import com.panonit.evently.domain.entities.TicketValidation;
import com.panonit.evently.exceptions.QrCodeNotFoundException;
import com.panonit.evently.exceptions.TicketTypeNotFoundException;
import com.panonit.evently.mapper.TicketValidationMapper;
import com.panonit.evently.repositories.QrCodeRepository;
import com.panonit.evently.repositories.TicketRepository;
import com.panonit.evently.repositories.TicketValidationRepository;
import com.panonit.evently.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketValidationMapper mapper;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeRepository qrCodeRepository;

    @Override
    @Transactional
    public TicketValidationResponseDto validateTicketByQrCode(UUID qrcodeId) {
        log.info("Validating ticket with ID {} by QR code", qrcodeId);

        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrcodeId, QrCodeStatus.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(String.format("Active QR code with ID %s not found", qrcodeId)));

        TicketValidation validation = validateTicket(qrCode.getTicket(), TicketValidationMethod.QR_SCAN);

        return mapper.toTicketValidationResponseDto(validation);
    }

    @Override
    @Transactional
    public TicketValidationResponseDto validateTicketManually(UUID ticketId) {
        log.info("Validating ticket with ID {} manually", ticketId);

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket with ID %s not found", ticketId)));

        TicketValidation validation = validateTicket(ticket, TicketValidationMethod.MANUAL);

        return mapper.toTicketValidationResponseDto(validation);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethod manual) {
        log.info("Validating ticket with ID {}", ticket.getId());

        TicketValidation validation = new TicketValidation();

        TicketValidationStatus status = ticket.getValidations().stream()
                .filter(v -> v.getStatus().equals(TicketValidationStatus.VALID))
                .findFirst()
                .map(v -> TicketValidationStatus.INVALID)
                .orElse(TicketValidationStatus.VALID);

        validation.setTicket(ticket);
        validation.setMethod(manual);
        validation.setStatus(status);

        return ticketValidationRepository.save(validation);
    }
}
