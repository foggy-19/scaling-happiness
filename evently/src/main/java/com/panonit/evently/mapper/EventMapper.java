package com.panonit.evently.mapper;


import com.panonit.evently.domain.dtos.*;
import com.panonit.evently.domain.entities.Event;
import com.panonit.evently.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateEventResponseDto toCreateEventResponseDto(Event event);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventResponseDto toGetEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

    GetPublishedEventResponseDto toGetPublishedEventResponseDto(Event event);

    @Mapping(source = "available", target = "totalAvailable")
    CreateTicketTypeResponseDto toCreateTicketTypeResponseDto(TicketType ticketType);

    @Mapping(source = "available", target = "totalAvailable")
    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    @Mapping(source = "available", target = "totalAvailable")
    ListTicketTypeResponseDto toListTicketTypeResponseDto(TicketType ticketType);

    @Mapping(source = "available", target = "totalAvailable")
    GetTicketTypeResponseDto toGetTicketTypeResponseDto(TicketType ticketType);

    GetPublishedTicketTypeResponseDto toGetPublishedTicketTypeResponseDto(TicketType ticketType);
}
