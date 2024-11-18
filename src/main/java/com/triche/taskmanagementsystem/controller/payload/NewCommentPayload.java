package com.triche.taskmanagementsystem.controller.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCommentPayload(
        @NotNull(message = "Comment should not be null")
        @NotBlank(message = "Comment should not be empty")
        @Schema(description = "Comment's text", example = "Task can't be finished because of legacy API")
        String text) {
}
