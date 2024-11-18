package com.triche.taskmanagementsystem.controller;

import com.triche.taskmanagementsystem.controller.payload.NewTaskPayload;
import com.triche.taskmanagementsystem.entity.Task;
import com.triche.taskmanagementsystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
@Tag(name = "Tasks", description = "Controller provides interactions with tasks")
public class TasksRestController {

    private final TaskService taskService;

    @Operation(
            summary = "Get all tasks",
            description = "Allows to get all tasks by title ignore case with pagination"
    )
    @GetMapping
    public Page<Task> getTasks(@RequestParam(name = "title", required = false) String title, Pageable pageable) {
        return taskService.getAllTasks(title, pageable);
    }

    @Operation(
            summary = "Get all tasks by assignee",
            description = "Allows to get all tasks by assignee id with pagination"
    )
    @GetMapping("/{assigneeId}/tasks")
    public Page<Task> getTasksByAssigneeId(@PathVariable Long assigneeId, Pageable pageable) {
        return taskService.getTasksByAssigneeId(assigneeId, pageable);
    }

    @Operation(
            summary = "Get all tasks by author",
            description = "Allows to get all tasks by author id with pagination"
    )
    @GetMapping("/{authorId}/tasks")
    public Page<Task> getTasksByAuthorId(@PathVariable Long authorId, Pageable pageable) {
        return taskService.getTasksByAuthorId(authorId, pageable);
    }

    @Operation(
            summary = "Create a new task",
            description = "Allows to create new task"
    )
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody @Valid NewTaskPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Task task = taskService.createTask(payload);
            return ResponseEntity
                    .created(uriBuilder
                            .path("{taskId}")
                            .build(Map.of("taskId", task.getId())))
                    .body(task);
        }
    }
}
