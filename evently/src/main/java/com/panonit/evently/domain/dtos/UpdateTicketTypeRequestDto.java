package com.panonit.evently.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketTypeRequestDto {

    private UUID id;

    @NotBlank(message = "Ticket type name is required")
    private String name;

    @PositiveOrZero(message = "Ticket type price must be zero or greater")
    private Double price;

    private String description;

    @PositiveOrZero(message = "Total available quantity must be zero or greater")
    private Integer totalAvailable;
}
