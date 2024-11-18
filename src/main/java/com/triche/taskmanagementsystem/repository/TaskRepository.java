package com.triche.taskmanagementsystem.repository;

import com.triche.taskmanagementsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<com.triche.taskmanagementsystem.entity.Task, Long> {

    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);

    Page<Task> findByAuthorId(Long authorId, Pageable pageable);

    Page<Task> findByTitleLikeIgnoreCase(String title, Pageable pageable);

    Page<Task> findByTitleLikeIgnoreCaseAndAssigneeUsername(String title, String username, Pageable pageable);

    Page<Task> findByAssigneeUsername(String username, Pageable pageable);
}
