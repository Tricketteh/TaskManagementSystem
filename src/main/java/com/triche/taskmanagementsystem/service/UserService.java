package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {


    void save(User user);

    void create(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

}
