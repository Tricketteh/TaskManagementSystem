package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.controller.payload.NewTaskPayload;
import com.triche.taskmanagementsystem.controller.payload.UpdateTaskPayload;
import com.triche.taskmanagementsystem.entity.Task;
import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.entity.enums.TaskStatus;
import com.triche.taskmanagementsystem.repository.TaskRepository;
import com.triche.taskmanagementsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
public class DefaultTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultTaskService taskService;

    private NewTaskPayload newTaskPayload;
    private UpdateTaskPayload updateTaskPayload;
    private Task task;
    private User user;

    // TODO before each setUp for test env

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(newTaskPayload);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Test Description", createdTask.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        taskService.updateTask(1L, updateTaskPayload);

        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateTaskStatus_whenTaskNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTaskStatus(1L, 1L, TaskStatus.IN_PROGRESS));
    }

    @Test
    public void testUpdateTaskStatus_whenUserNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTaskStatus(1L, 1L, TaskStatus.IN_PROGRESS));
    }

    @Test
    public void testGetAllTasks_forUserRole() {
        Pageable pageable = mock(Pageable.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findByAssigneeUsername(anyString(), eq(pageable))).thenReturn(new PageImpl<>(List.of(task)));

        Page<Task> tasks = taskService.getAllTasks(null, pageable);

        assertNotNull(tasks);
        assertEquals(1, tasks.getContent().size());
        verify(taskRepository, times(1)).findByAssigneeUsername(anyString(), eq(pageable));
    }

    @Test
    public void testGetTaskById_forUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1L);

        assertTrue(foundTask.isPresent());
        assertEquals("Test Task", foundTask.get().getTitle());
    }

    @Test
    public void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(anyLong());

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(anyLong());
    }
}
