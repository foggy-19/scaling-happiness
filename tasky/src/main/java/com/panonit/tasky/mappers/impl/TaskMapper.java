package com.panonit.tasky.mappers.impl;

import com.panonit.tasky.domain.dto.TaskDto;
import com.panonit.tasky.domain.entities.TaskEntity;
import com.panonit.tasky.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements Mapper<TaskEntity, TaskDto> {

    @Override
    public TaskDto toDto(TaskEntity entity) {
        return new TaskDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDeadline(),
                entity.getStatus(),
                entity.getPriority()
        );
    }

    @Override
    public TaskEntity toEntity(TaskDto dto) {
        return new TaskEntity(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.deadline(),
                dto.status(),
                dto.priority(),
                null,
                null,
                null
        );
    }
}
