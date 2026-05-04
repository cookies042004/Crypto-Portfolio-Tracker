package com.crypto.auth_service.controller;

import com.crypto.auth_service.dto.AuthResponse;
import com.crypto.auth_service.dto.LoginRequest;
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

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}