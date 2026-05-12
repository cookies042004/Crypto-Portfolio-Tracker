package com.crypto.auth_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    // User database ID
    private Long id;

    // Username
    private String username;

    // User email
    private String email;

    // User role
    private String role;
}