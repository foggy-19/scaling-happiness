package com.panonit.tasky.controllers;

import com.panonit.tasky.domain.dto.TaskListDto;
import com.panonit.tasky.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists")
public class TaskListController {

    private final TaskListService service;

    public TaskListController(TaskListService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return service.listTaskLists();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        return service.createTaskList(taskListDto);
    }

    @GetMapping(path = "/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListId) {
        return service.getTaskList(taskListId);
    }

    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskListDto taskListDto) {
        return service.updateTaskList(taskListId, taskListDto);
    }

    @DeleteMapping(path = "/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {
        service.deleteTaskList(taskListId);
    }
}
