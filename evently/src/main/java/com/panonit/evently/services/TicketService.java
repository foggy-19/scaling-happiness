package com.panonit.evently.services;

import com.panonit.evently.domain.dtos.GetTicketResponseDto;
import com.panonit.evently.domain.dtos.ListTicketResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    Page<ListTicketResponseDto> listTicketsForUser(UUID userId, Pageable pageable);

    Optional<GetTicketResponseDto> getTicketForUser(UUID userId, UUID ticketId);
}
