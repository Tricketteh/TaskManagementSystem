package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.entity.Comment;
import com.triche.taskmanagementsystem.entity.Task;
import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.entity.enums.Role;
import com.triche.taskmanagementsystem.repository.CommentRepository;
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

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public Comment createComment(Long taskId, Long userId, String text) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (!task.getAssignee().equals(user) && !user.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not allowed to get task");
        }
        return Comment.builder()
                .text(text)
                .author(user)
                .task(task)
                .build();
    }

    @Override
    public Page<Comment> getCommentsByTaskId(Long taskId, Pageable pageable) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();
        String assigneeUsername = taskRepository.findById(taskId).get().getAssignee().getUsername();
        Role currentRole = userRepository.findByUsername(currentUsername).get().getRole();

        if (!assigneeUsername.equals(currentUsername) && !currentRole.equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not allowed to get tasks");
        }
        return commentRepository.findByTaskId(taskId, pageable);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
