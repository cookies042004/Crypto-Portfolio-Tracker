package com.crypto.auth_service.service;

import com.crypto.auth_service.dto.LoginRequest;
import com.crypto.auth_service.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    String login(LoginRequest request);
}