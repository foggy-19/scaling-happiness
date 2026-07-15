package com.panonit.evently.domain.dtos;

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
public class ListPublishedEventResponseDto {

    private UUID id;
    private String name;
    private String venue;
    private LocalDateTime start;
    private LocalDateTime end;
}
