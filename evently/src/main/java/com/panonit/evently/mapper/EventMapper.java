package com.panonit.evently.mapper;


import com.panonit.evently.domain.dtos.CreateEventResponseDto;
import com.panonit.evently.domain.dtos.CreateTicketTypeResponseDto;
import com.panonit.evently.domain.dtos.EventDto;
import com.panonit.evently.domain.dtos.TicketTypeDto;
import com.panonit.evently.domain.entities.Event;
import com.panonit.evently.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    @Mapping(source = "ticketTypes", target = "ticketTypes", qualifiedByName = "toTicketTypeDto")
    EventDto toEventDto(Event event);

    @Mapping(source = "ticketTypes", target = "ticketTypes", qualifiedByName = "toCreateTicketTypeResponseDto")
    CreateEventResponseDto toCreateEventResponseDto(Event event);

    @Named("toTicketTypeDto")
    @Mapping(source = "available", target = "totalAvailable")
    TicketTypeDto toTicketTypeDto(TicketType ticketType);

    @Named("toCreateTicketTypeResponseDto")
    @Mapping(source = "available", target = "totalAvailable")
    CreateTicketTypeResponseDto toCreateTicketTypeResponseDto(TicketType ticketType);
}
