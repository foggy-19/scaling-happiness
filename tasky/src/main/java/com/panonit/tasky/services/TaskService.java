package com.panonit.tasky.services;

import com.panonit.tasky.domain.dto.TaskDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId);

    TaskDto createTask(UUID taskListId, TaskDto taskDto);

    Optional<TaskDto> getTask(UUID taskListId, UUID taskId);

    TaskDto updateTask(UUID taskListId, UUID taskId, TaskDto taskDto);

    void deleteTask(UUID taskListId, UUID taskId);
}
