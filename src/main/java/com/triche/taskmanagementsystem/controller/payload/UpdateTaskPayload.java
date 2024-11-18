package com.triche.taskmanagementsystem.controller.payload;

import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.entity.enums.TaskPriority;
import com.triche.taskmanagementsystem.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateTaskPayload(
        @NotBlank(message = "Title should not be empty")
        @Size(min = 2, max = 255, message = "Title size should be in range of 2 to 255 symbols")
        String title,
        @NotBlank(message = "Description should not be empty")
        String description,
        @NotNull(message = "Status should not be empty")
        TaskStatus status,
        @NotNull(message = "Priority should not be empty")
        TaskPriority priority,
        @NotNull(message = "Assignee should not be empty")
        User assignee
) {
}
