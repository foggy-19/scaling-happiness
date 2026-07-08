package com.panonit.tasky.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.panonit.tasky.domain.entities.TaskPriority;
import com.panonit.tasky.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

// record == immutable data class

public record TaskDto(
        UUID id,
        String title,
        String description,
        @JsonProperty("dueDate")
        LocalDateTime deadline,
        TaskStatus status,
        TaskPriority priority
) {

}
