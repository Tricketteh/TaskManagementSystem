package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.controller.payload.NewTaskPayload;
import com.triche.taskmanagementsystem.controller.payload.UpdateTaskPayload;
import com.triche.taskmanagementsystem.entity.Task;
import com.triche.taskmanagementsystem.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskService {

    Task createTask(NewTaskPayload newTaskPayload);

    void updateTask(Long id, UpdateTaskPayload updateTaskPayload);

    void updateTaskStatus(Long taskId, Long userId, TaskStatus status);

    Page<Task> getAllTasks(String title, Pageable pageable);

    Page<Task> getTasksByAuthorId(Long authorId, Pageable pageable);

    Page<Task> getTasksByAssigneeId(Long assigneeId, Pageable pageable);

    Optional<Task> getTaskById(Long id);

    void deleteTask(Long id);
}
