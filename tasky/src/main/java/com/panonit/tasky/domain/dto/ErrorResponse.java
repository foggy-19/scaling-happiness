package com.panonit.tasky.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
