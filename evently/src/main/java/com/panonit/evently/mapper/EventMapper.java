package com.panonit.evently.mapper;


import com.panonit.evently.domain.dtos.*;
import com.panonit.evently.domain.entities.Event;
import com.panonit.evently.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    @Mapping(source = "ticketTypes", target = "ticketTypes", qualifiedByName = "toCreateTicketTypeResponseDto")
    CreateEventResponseDto toCreateEventResponseDto(Event event);

    @Mapping(source = "ticketTypes", target = "ticketTypes", qualifiedByName = "toUpdateTicketTypeResponseDto")
    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    @Mapping(source = "ticketTypes", target = "ticketTypes", qualifiedByName = "toListTicketTypeResponseDto")
    ListEventResponseDto toListEventResponseDto(Event event);

    @Mapping(source = "ticketTypes", target = "ticketTypes", qualifiedByName = "toGetTicketTypeResponseDto")
    GetEventResponseDto toGetEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

    @Named("toCreateTicketTypeResponseDto")
    @Mapping(source = "available", target = "totalAvailable")
    CreateTicketTypeResponseDto toCreateTicketTypeResponseDto(TicketType ticketType);

    @Named("toUpdateTicketTypeResponseDto")
    @Mapping(source = "available", target = "totalAvailable")
    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    @Named("toListTicketTypeResponseDto")
    @Mapping(source = "available", target = "totalAvailable")
    ListTicketTypeResponseDto toListTicketTypeResponseDto(TicketType ticketType);

    @Named("toGetTicketTypeResponseDto")
    @Mapping(source = "available", target = "totalAvailable")
    GetTicketTypeResponseDto toGetTicketTypeResponseDto(TicketType ticketType);
}
