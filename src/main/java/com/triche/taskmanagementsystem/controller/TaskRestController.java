package com.triche.taskmanagementsystem.controller;

import com.triche.taskmanagementsystem.controller.payload.UpdateTaskPayload;
import com.triche.taskmanagementsystem.entity.Task;
import com.triche.taskmanagementsystem.entity.enums.TaskStatus;
import com.triche.taskmanagementsystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks/{taskId:\\d+}")
@Tag(name = "Tasks", description = "Controller provides interactions with a single task")
public class TaskRestController {

    private final TaskService taskService;

    @ModelAttribute("task")
    public Task getTask(@PathVariable("taskId") Long taskId) {
        return taskService.getTaskById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    @Operation(
            summary = "Get task",
            description = "Allow to get task by id"
    )
    @GetMapping()
    public Task getTask(@ModelAttribute("task") Task task) {
        return task;
    }

    @Operation(
            summary = "Updating task",
            description = "Allows admin to update task"
    )
    @PatchMapping("/update")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") Long taskId,
                                           @RequestBody @Valid UpdateTaskPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.taskService.updateTask(taskId, payload);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @Operation(
            summary = "Updating task status",
            description = "Allows user to update task status"
    )
    @PatchMapping("/updateStatus")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable("taskId") Long taskId, Long userId,
                                                 @RequestBody TaskStatus status,
                                                 BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            taskService.updateTaskStatus(taskId, userId, status);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @Operation(
            summary = "Delete task",
            description = "Allows to delete task"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        this.taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }


}
