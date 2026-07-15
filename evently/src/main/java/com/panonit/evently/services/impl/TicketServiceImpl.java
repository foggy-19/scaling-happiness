package com.panonit.evently.services.impl;

import com.panonit.evently.domain.dtos.GetTicketResponseDto;
import com.panonit.evently.domain.dtos.ListTicketResponseDto;
import com.panonit.evently.mapper.TicketMapper;
import com.panonit.evently.repositories.TicketRepository;
import com.panonit.evently.services.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketMapper mapper;
    private final TicketRepository repository;

    @Override
    public Page<ListTicketResponseDto> listTicketsForUser(UUID userId, Pageable pageable) {
        log.info("Listing tickets for user {}", userId);

        return repository.findByPurchaserId(userId, pageable).map(mapper::toListTicketResponseDto);
    }

    @Override
    public Optional<GetTicketResponseDto> getTicketForUser(UUID userId, UUID ticketId) {
        log.info("Getting ticket for user {}", userId);

        return repository.findByIdAndPurchaserId(ticketId, userId).map(mapper::toGetTicketResponseDto);
    }
}
