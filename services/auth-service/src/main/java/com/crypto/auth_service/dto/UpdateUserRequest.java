package com.crypto.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    // Updated username
    @NotBlank(message = "Username is required")
    private String username;

    // Updated email
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
}