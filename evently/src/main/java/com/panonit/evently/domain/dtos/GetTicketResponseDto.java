package com.panonit.evently.domain.dtos;

import com.panonit.evently.domain.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketResponseDto {

    private UUID id;

    private TicketStatus status;

    private Double price;

    private String description;

    private String eventName;

    private String eventVenue;

    private LocalDateTime eventStart;

    private LocalDateTime eventEnd;
}
