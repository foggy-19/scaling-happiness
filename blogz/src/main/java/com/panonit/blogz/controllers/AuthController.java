package com.panonit.blogz.controllers;

import com.panonit.blogz.domain.dtos.AuthResponse;
import com.panonit.blogz.domain.dtos.LoginRequest;
import com.panonit.blogz.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final long dayInSeconds = 24 * 60 * 60;

    private final AuthenticationService service;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = service.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        AuthResponse response = AuthResponse.builder()
                .token(service.generateToken(userDetails))
                .expiresIn(dayInSeconds)
                .build();

        return ResponseEntity.ok(response);
    }
}
