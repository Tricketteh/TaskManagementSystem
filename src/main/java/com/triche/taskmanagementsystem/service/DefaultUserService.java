package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.entity.User;
import com.triche.taskmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("This username is already taken");
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("This email is already taken");
        }
        save(user);
    }

    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

}
