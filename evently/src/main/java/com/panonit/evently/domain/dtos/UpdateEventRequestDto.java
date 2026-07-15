package com.panonit.evently.domain.dtos;

import com.panonit.evently.domain.EventStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequestDto {

    @NotNull(message = "Event ID is required")
    private UUID id;

    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Venue description is required")
    private String venue;

    private LocalDateTime start;

    private LocalDateTime end;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @NotNull(message = "Event status is required")
    private EventStatus status;

    @NotEmpty(message = "At least one ticket type is required")
    private List<@Valid UpdateTicketTypeRequestDto> ticketTypes;
}
