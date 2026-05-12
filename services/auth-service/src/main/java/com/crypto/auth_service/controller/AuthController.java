package com.crypto.auth_service.controller;

import com.crypto.auth_service.dto.AuthResponse;
import com.crypto.auth_service.dto.LoginRequest;
import com.crypto.auth_service.dto.RefreshRequest;
import com.crypto.auth_service.dto.RegisterRequest;
import com.crypto.auth_service.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Service layer dependency
    private final AuthService authService;

    /**
     * Register new user
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        return ResponseEntity.ok(
                "User registered successfully"
        );
    }

    /**
     * Login user and generate
     * access + refresh token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        AuthResponse response =
                authService.login(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Generate new access token
     * using refresh token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody RefreshRequest request
    ) {

        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {

        // Remove Bearer prefix
        String token = authHeader.substring(7);

        authService.logout(token);

        return ResponseEntity.ok(
                "Logged out successfully"
        );
    }
}