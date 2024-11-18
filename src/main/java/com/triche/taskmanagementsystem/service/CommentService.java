package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment createComment(Long taskId, Long userId, String text);

    Page<Comment> getCommentsByTaskId(Long taskId, Pageable pageable);

    void deleteComment(Long commentId);
}
