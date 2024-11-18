package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .username("testuser")
                .email("testuser@test.com")
                .password("password")
                .build();
    }

    @Test
    public void testSave() {
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreate_whenUsernameExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.create(user));
        assertEquals("This username is already taken", exception.getMessage());
    }

    @Test
    public void testCreate_whenEmailExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.create(user));
        assertEquals("This email is already taken", exception.getMessage());
    }

    @Test
    public void testCreate_whenUsernameAndEmailAreUnique() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.create(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetByUsername_whenUserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername(user.getUsername()));
        assertEquals("Username testuser not found", exception.getMessage());
    }

    @Test
    public void testGetByUsername_whenUserFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundUser = userService.getByUsername(user.getUsername());
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }
}