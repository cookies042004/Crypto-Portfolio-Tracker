package com.crypto.auth_service.service;

import com.crypto.auth_service.dto.ChangePasswordRequest;
import com.crypto.auth_service.dto.UpdateUserRequest;
import com.crypto.auth_service.dto.UserResponse;

public interface UserService {

    UserResponse getCurrentUser(String email);

    UserResponse updateCurrentUser(String currentEmail, UpdateUserRequest request);

    void changePassword(String email, ChangePasswordRequest request);
}