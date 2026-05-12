package com.crypto.auth_service.controller;

import com.crypto.auth_service.dto.ChangePasswordRequest;
import com.crypto.auth_service.dto.UpdateUserRequest;
import com.crypto.auth_service.dto.UserResponse;

import com.crypto.auth_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Returns details of currently logged-in user.
     */
    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {

        // Extract logged-in user's email
        String email = authentication.getName();

        return userService.getCurrentUser(email);
    }

    @PutMapping("/me")
    public UserResponse updateCurrentUser(Authentication authentication, @Valid @RequestBody
                    UpdateUserRequest request) {

        // Extract logged-in user's email
        String email = authentication.getName();

        return userService.updateCurrentUser(email, request);
    }

    @PutMapping("/change-password")
    public String changePassword(Authentication authentication, @Valid @RequestBody ChangePasswordRequest request) {

        // Extract logged-in user's email
        String email = authentication.getName();

        userService.changePassword(email, request);

        return "Password changed successfully";
    }
}