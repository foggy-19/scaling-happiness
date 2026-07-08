package com.panonit.tasky.services;

import com.panonit.tasky.domain.dto.TaskListDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {

    List<TaskListDto> listTaskLists();

    TaskListDto createTaskList(TaskListDto taskListDto);

    Optional<TaskListDto> getTaskList(UUID taskListId);

    TaskListDto updateTaskList(UUID taskListId, TaskListDto taskListDto);

    void deleteTaskList(UUID taskListId);
}
