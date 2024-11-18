package com.triche.taskmanagementsystem.service;

import com.triche.taskmanagementsystem.dto.JwtAuthenticationResponse;
import com.triche.taskmanagementsystem.dto.SignInRequest;
import com.triche.taskmanagementsystem.dto.SignUpRequest;

public interface AuthenticationService {


    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);

}

