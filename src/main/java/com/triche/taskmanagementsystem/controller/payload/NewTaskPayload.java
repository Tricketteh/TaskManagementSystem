package com.triche.taskmanagementsystem.controller.payload;

import com.triche.taskmanagementsystem.entity.Comment;
import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.entity.enums.TaskPriority;
import com.triche.taskmanagementsystem.entity.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewTaskPayload(
        @NotBlank(message = "Title should not be empty")
        @Size(min = 2, max = 255, message = "Title size should be in range of 2 to 255 symbols")
        @Schema(description = "Task title", example = "Fix bugs in auth module")
        String title,
        @NotBlank(message = "Description should not be empty")
        @Schema(description = "Task description", example = "Users can't sign in")
        String description,
        @NotNull(message = "Status should not be empty")
        @Schema(description = "Task status", example = "PENDING")
        TaskStatus status,
        @NotNull(message = "Priority should not be empty")
        @Schema(description = "Task priority", example = "HIGH")
        TaskPriority priority,
        @NotNull(message = "Author should not be empty")
        @Schema(description = "Task author", example = "Jane Doe")
        User author,
        @NotNull(message = "Assignee should not be empty")
        @Schema(description = "Task assignee", example = "Jack Daniels")
        User assignee,
        List<Comment> comments
) {
}
