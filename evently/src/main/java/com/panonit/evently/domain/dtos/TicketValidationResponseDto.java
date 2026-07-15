package com.panonit.evently.domain.dtos;

import com.panonit.evently.domain.TicketValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketValidationResponseDto {

    private UUID ticketId;

    private TicketValidationStatus status;
}
