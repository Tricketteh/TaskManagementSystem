package com.triche.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triche.taskmanagementsystem.dto.JwtAuthenticationResponse;
import com.triche.taskmanagementsystem.dto.SignInRequest;
import com.triche.taskmanagementsystem.dto.SignUpRequest;
import com.triche.taskmanagementsystem.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;
    private JwtAuthenticationResponse jwtAuthenticationResponse;

    @BeforeEach
    public void setUp() {
        signUpRequest = new SignUpRequest("testuser", "password123", "testuser@example.com");
        signInRequest = new SignInRequest("testuser", "password123");
        jwtAuthenticationResponse = new JwtAuthenticationResponse("dummy-jwt-token");
    }

    @Test
    public void testSignUp() throws Exception {
        when(authenticationService.signUp(signUpRequest)).thenReturn(jwtAuthenticationResponse);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-jwt-token"));

        verify(authenticationService, times(1)).signUp(signUpRequest);
    }

    @Test
    public void testSignIn() throws Exception {
        when(authenticationService.signIn(signInRequest)).thenReturn(jwtAuthenticationResponse);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-jwt-token"));

        verify(authenticationService, times(1)).signIn(signInRequest);
    }
}

