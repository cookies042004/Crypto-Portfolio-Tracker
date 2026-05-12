package com.crypto.auth_service.service.impl;

import com.crypto.auth_service.dto.ChangePasswordRequest;
import com.crypto.auth_service.dto.UpdateUserRequest;
import com.crypto.auth_service.dto.UserResponse;
import com.crypto.auth_service.entity.User;
import com.crypto.auth_service.exception.InvalidCredentialsException;
import com.crypto.auth_service.exception.ResourceAlreadyExistsException;
import com.crypto.auth_service.exception.ResourceNotFoundException;
import com.crypto.auth_service.repository.UserRepository;
import com.crypto.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Fetches currently authenticated user details.
     */
    @Override
    public UserResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public UserResponse updateCurrentUser(String currentEmail, UpdateUserRequest request) {

        // Fetch current user
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        // Check if new email already exists
        userRepository.findByEmail(request.getEmail())
                .ifPresent(existingUser -> {

                    // Allow same user's current email
                    if (!existingUser.getId().equals(user.getId())) {

                        throw new ResourceAlreadyExistsException("Email already in use");
                    }
                });

        // Update fields
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Save updated user
        User updatedUser = userRepository.save(user);

        // Return response DTO
        return UserResponse.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole().name())
                .build();
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {

        // Fetch logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        // Verify current password
        boolean matches = passwordEncoder.matches(request.getCurrentPassword(), user.getPassword());

        if (!matches) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        // Encode and update new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}