package com.triche.taskmanagementsystem.controller;

import com.triche.taskmanagementsystem.controller.payload.NewCommentPayload;
import com.triche.taskmanagementsystem.entity.Comment;
import com.triche.taskmanagementsystem.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Controller", description = "Comment controller provides endpoints for comments interactions")
public class CommentRestController {

    private final CommentService commentService;

    @Operation(
            summary = "Creating a new comment for task by task id and user id",
            description = "Allows to add new comment for a task"
    )
    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestParam Long taskId,
                                                 @RequestParam Long userId,
                                                 @RequestBody @Valid NewCommentPayload payload) {
        Comment createdComment = commentService.createComment(taskId, userId, payload.text());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdComment);
    }

    @Operation(
            summary = "Getting all comments under a task by task id",
            description = "Allows to get all comments for a task using pagination"
    )
    @GetMapping("/task/{taskId}")
    public ResponseEntity<Page<Comment>> getCommentsByTask(@PathVariable Long taskId, Pageable pageable) {
        Page<Comment> comments = commentService.getCommentsByTaskId(taskId, pageable);
        return ResponseEntity
                .ok(comments);
    }

    @Operation(
            summary = "Delete comment",
            description = "Allows to delete a comment under a task"
    )
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
