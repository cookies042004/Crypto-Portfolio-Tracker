package com.crypto.auth_service.service;

import com.crypto.auth_service.dto.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);
}