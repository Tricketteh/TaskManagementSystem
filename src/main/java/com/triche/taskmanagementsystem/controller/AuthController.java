package com.triche.taskmanagementsystem.controller;

import com.triche.taskmanagementsystem.dto.JwtAuthenticationResponse;
import com.triche.taskmanagementsystem.dto.SignInRequest;
import com.triche.taskmanagementsystem.dto.SignUpRequest;
import com.triche.taskmanagementsystem.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication controller providing access to login and registration endpoints")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User registration", description = "Allows user to register")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "User login", description = "Allows user to login")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
