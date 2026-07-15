package com.panonit.evently.services;

import com.panonit.evently.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    void generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
