package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.controller.payload.NewTaskPayload;
import com.triche.taskmanagementsystem.controller.payload.UpdateTaskPayload;
import com.triche.taskmanagementsystem.entity.Task;
import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.entity.enums.Role;
import com.triche.taskmanagementsystem.entity.enums.TaskStatus;
import com.triche.taskmanagementsystem.repository.TaskRepository;
import com.triche.taskmanagementsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Task createTask(NewTaskPayload payload) {
        return taskRepository
                .save(Task.builder()
                        .id(null)
                        .title(payload.title())
                        .description(payload.description())
                        .status(payload.status())
                        .priority(payload.priority())
                        .author(payload.author())
                        .assignee(payload.assignee())
                        .comments(payload.comments())
                        .build());
    }

    @Override
    @Transactional
    public void updateTask(Long id, UpdateTaskPayload payload) {
        taskRepository.findById(id).ifPresentOrElse(task -> {
            task.setTitle(payload.title());
            task.setDescription(payload.description());
            task.setStatus(payload.status());
            task.setPriority(payload.priority());
            task.setAssignee(payload.assignee());
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    @Override
    @Transactional
    public void updateTaskStatus(Long taskId, Long userId, TaskStatus status) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (task.getAssignee().equals(user)) {
            task.setStatus(status);
        }
        throw new AccessDeniedException("You are not allowed to update task priority");
    }

    @Override
    public Page<Task> getAllTasks(String title, Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();
        Role currentRole = userRepository.findByUsername(currentUsername).get().getRole();

        if (currentRole == Role.ROLE_ADMIN) {
            if (title != null && !title.isBlank()) {
                return taskRepository.findByTitleLikeIgnoreCase(title, pageable);
            }
            return taskRepository.findAll(pageable);
        } else {
            if (title != null && !title.isBlank()) {
                return taskRepository.findByTitleLikeIgnoreCaseAndAssigneeUsername(title, currentUsername, pageable);
            }
            return taskRepository.findByAssigneeUsername(currentUsername, pageable);
        }
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();
        Role currentRole = userRepository.findByUsername(currentUsername).get().getRole();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        if (currentRole == Role.ROLE_ADMIN) {
            return taskRepository.findById(id);
        }
        if (!task.getAssignee().equals(userRepository.findByUsername(currentUsername).get())) {
            throw new AccessDeniedException("You are not allowed to get task");
        }
        return taskRepository.findById(id);
    }

    @Override
    public Page<Task> getTasksByAuthorId(Long authorId, Pageable pageable) {
        return taskRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    public Page<Task> getTasksByAssigneeId(Long assigneeId, Pageable pageable) {
        return taskRepository.findByAssigneeId(assigneeId, pageable);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
