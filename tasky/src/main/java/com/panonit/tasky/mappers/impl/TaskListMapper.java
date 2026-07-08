package com.panonit.tasky.mappers.impl;

import com.panonit.tasky.domain.dto.TaskDto;
import com.panonit.tasky.domain.dto.TaskListDto;
import com.panonit.tasky.domain.entities.TaskEntity;
import com.panonit.tasky.domain.entities.TaskListEntity;
import com.panonit.tasky.domain.entities.TaskStatus;
import com.panonit.tasky.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapper implements Mapper<TaskListEntity, TaskListDto> {

    private final Mapper<TaskEntity, TaskDto> taskMapper;

    public TaskListMapper(Mapper<TaskEntity, TaskDto> taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskListDto toDto(TaskListEntity entity) {
        return new TaskListDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                Optional.ofNullable(entity.getTasks()).map(tasks -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null),
                count(entity.getTasks()),
                progress(entity.getTasks())
        );
    }

    @Override
    public TaskListEntity toEntity(TaskListDto dto) {
        return new TaskListEntity(
                dto.id(),
                dto.title(),
                dto.description(),
                Optional.ofNullable(dto.tasks()).map(tasks -> tasks.stream().map(taskMapper::toEntity).toList()).orElse(null),
                null,
                null
        );
    }

    private Integer count(List<TaskEntity> tasks) {
        if (tasks == null) {
            return 0;
        }

        return tasks.size();
    }

    private Double progress(List<TaskEntity> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }

        int count = tasks.size();
        long closed = tasks.stream().filter(task -> task.getStatus() == TaskStatus.CLOSED).count();

        return closed / count * 100.0;
    }
}
