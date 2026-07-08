package com.panonit.tasky.services.impl;

import com.panonit.tasky.domain.dto.TaskDto;
import com.panonit.tasky.domain.entities.TaskEntity;
import com.panonit.tasky.domain.entities.TaskListEntity;
import com.panonit.tasky.domain.entities.TaskPriority;
import com.panonit.tasky.domain.entities.TaskStatus;
import com.panonit.tasky.mappers.Mapper;
import com.panonit.tasky.repositories.TaskListRepository;
import com.panonit.tasky.repositories.TaskRepository;
import com.panonit.tasky.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    private final Mapper<TaskEntity, TaskDto> mapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository, Mapper<TaskEntity, TaskDto> mapper) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TaskDto> listTasks(UUID taskListId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("taskListId cannot be null");
        }

        return taskRepository.findByTaskListId(taskListId).stream().map(mapper::toDto).toList();
    }

    @Override
    public TaskDto createTask(UUID taskListId, TaskDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException("Task cannot be null!");
        }
        if (taskDto.id() != null) {
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if (taskDto.title() == null || taskDto.title().isBlank()) {
            throw new IllegalArgumentException("Task title is required!");
        }

        TaskListEntity taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task list ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        TaskPriority taskPriority = Optional.ofNullable(taskDto.priority()).orElse(TaskPriority.MEDIUM);

        TaskEntity entity = TaskEntity.builder()
                .title(taskDto.title())
                .description(taskDto.description())
                .deadline(taskDto.deadline())
                .priority(taskPriority)
                .status(TaskStatus.OPEN)
                .taskList(taskList)
                .createdAt(now)
                .updatedAt(now)
                .build();

        return mapper.toDto(taskRepository.save(entity));
    }

    @Override
    public Optional<TaskDto> getTask(UUID taskListId, UUID taskId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("Task list ID cannot be null!");
        }
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null!");
        }

        return taskRepository.findByTaskListIdAndId(taskListId, taskId).map(mapper::toDto);
    }


    @Override
    @Transactional
    public TaskDto updateTask(UUID taskListId, UUID taskId, TaskDto taskDto) {
        if (taskListId == null) {
            throw new IllegalArgumentException("Task list ID cannot be null!");
        }
        if (taskDto.id() == null) {
            throw new IllegalArgumentException("Task must have an ID!");
        }
        if (!Objects.equals(taskId, taskDto.id())) {
            throw new IllegalArgumentException("Task IDs do not match!");
        }
        if (taskDto.title() == null || taskDto.title().isBlank()) {
            throw new IllegalArgumentException("Task title is required!");
        }
        if (taskDto.priority() == null) {
            throw new IllegalArgumentException("Task priority is required!");
        }
        if (taskDto.status() == null) {
            throw new IllegalArgumentException("Task status is required!");
        }

        TaskEntity entity = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found!"));

        entity.setTitle(taskDto.title());
        entity.setDescription(taskDto.description());
        entity.setDeadline(taskDto.deadline());
        entity.setPriority(taskDto.priority());
        entity.setStatus(taskDto.status());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(taskRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskListId, UUID taskId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("Task list mast have an ID!");
        }

        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
