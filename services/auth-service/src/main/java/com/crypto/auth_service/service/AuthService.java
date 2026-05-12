package com.crypto.auth_service.service;

import com.crypto.auth_service.dto.AuthResponse;
import com.crypto.auth_service.dto.LoginRequest;
import com.crypto.auth_service.dto.RefreshRequest;
import com.crypto.auth_service.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshRequest request);
    void logout(String token);
}