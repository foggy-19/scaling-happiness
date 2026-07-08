package com.panonit.tasky.services.impl;

import com.panonit.tasky.domain.dto.TaskListDto;
import com.panonit.tasky.domain.entities.TaskListEntity;
import com.panonit.tasky.mappers.Mapper;
import com.panonit.tasky.repositories.TaskListRepository;
import com.panonit.tasky.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository repository;

    private final Mapper<TaskListEntity, TaskListDto> mapper;

    public TaskListServiceImpl(TaskListRepository repository, Mapper<TaskListEntity, TaskListDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TaskListDto> listTaskLists() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public TaskListDto createTaskList(TaskListDto taskListDto) {
        if (taskListDto.id() != null) {
            throw new IllegalArgumentException("Task list already has an ID!");
        }

        if (taskListDto.title() == null || taskListDto.title().isEmpty()) {
            throw new IllegalArgumentException("Task list title is required!");
        }

        LocalDateTime now = LocalDateTime.now();
        TaskListEntity entity = TaskListEntity.builder()
                .title(taskListDto.title())
                .description(taskListDto.description())
                .createdAt(now)
                .updatedAt(now)
                .build();

        return mapper.toDto(repository.save(entity));
    }

    @Override
    public Optional<TaskListDto> getTaskList(UUID taskListId) {
        return repository.findById(taskListId).map(mapper::toDto);
    }

    @Override
    @Transactional
    public TaskListDto updateTaskList(UUID taskListId, TaskListDto taskListDto) {
        if (taskListDto.id() == null) {
            throw new IllegalArgumentException("Task list must have an ID!");
        }

        if (!Objects.equals(taskListId, taskListDto.id())) {
            throw new IllegalArgumentException("Task list ID does not match!");
        }

        TaskListEntity entity = repository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list ID does not exist!"));

        entity.setTitle(taskListDto.title());
        entity.setDescription(taskListDto.description());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("Task list must have an ID!");
        }

        repository.deleteById(taskListId);
    }
}
