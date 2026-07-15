package com.panonit.evently.services.impl;

import com.panonit.evently.domain.TicketStatus;
import com.panonit.evently.domain.entities.Ticket;
import com.panonit.evently.domain.entities.TicketType;
import com.panonit.evently.domain.entities.User;
import com.panonit.evently.exceptions.TicketSoldOutException;
import com.panonit.evently.exceptions.TicketTypeNotFoundException;
import com.panonit.evently.exceptions.UserNotFoundException;
import com.panonit.evently.repositories.TicketRepository;
import com.panonit.evently.repositories.TicketTypeRepository;
import com.panonit.evently.repositories.UserRepository;
import com.panonit.evently.services.QrCodeService;
import com.panonit.evently.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        log.info("Purchasing ticket for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s was not found", userId)));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket type with ID %s was not found", ticketTypeId)));

        int purchasedTickets = ticketRepository.countByType(ticketType);
        if (purchasedTickets + 1 > ticketType.getAvailable()) {
            throw new TicketSoldOutException(String.format("Ticket type with ID %s is sold out", ticketTypeId));
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatus.PURCHASED);
        ticket.setType(ticketType);
        ticket.setPurchaser(user);

        ticket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(ticket);

        return ticketRepository.save(ticket);
    }
}
