package com.panonit.evently.mapper;

import com.panonit.evently.domain.dtos.GetTicketResponseDto;
import com.panonit.evently.domain.dtos.ListTicketResponseDto;
import com.panonit.evently.domain.dtos.ListTicketTicketTypeResponseDto;
import com.panonit.evently.domain.entities.Ticket;
import com.panonit.evently.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    @Mapping(source = "type", target = "ticketType", qualifiedByName = "toListTicketTicketTypeResponseDto")
    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Named("toListTicketTicketTypeResponseDto")
    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    @Mapping(source = "ticket.type.price", target = "price")
    @Mapping(source = "ticket.type.description", target = "description")
    @Mapping(source = "ticket.type.event.name", target = "eventName")
    @Mapping(source = "ticket.type.event.venue", target = "eventVenue")
    @Mapping(source = "ticket.type.event.start", target = "eventStart")
    @Mapping(source = "ticket.type.event.end", target = "eventEnd")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}
