package com.crypto.auth_service.service.impl;

import com.crypto.auth_service.dto.UserResponse;
import com.crypto.auth_service.entity.User;
import com.crypto.auth_service.exception.ResourceNotFoundException;
import com.crypto.auth_service.repository.UserRepository;
import com.crypto.auth_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    /**
     * Fetch all users.
     */
    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Fetch user by ID.
     */
    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        return mapToResponse(user);
    }

    /**
     * Delete user by ID.
     */
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        userRepository.delete(user);
    }

    /**
     * Convert entity into DTO.
     */
    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}