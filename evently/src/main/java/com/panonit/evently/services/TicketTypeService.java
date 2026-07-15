package com.panonit.evently.services;

import com.panonit.evently.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
