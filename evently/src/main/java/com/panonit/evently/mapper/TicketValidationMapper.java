package com.panonit.evently.mapper;

import com.panonit.evently.domain.dtos.TicketValidationResponseDto;
import com.panonit.evently.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation validation);
}
