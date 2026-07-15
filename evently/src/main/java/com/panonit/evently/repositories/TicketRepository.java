package com.panonit.evently.repositories;

import com.panonit.evently.domain.entities.Ticket;
import com.panonit.evently.domain.entities.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    int countByType(TicketType ticketType);
}
