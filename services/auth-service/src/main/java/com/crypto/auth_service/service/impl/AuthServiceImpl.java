package com.crypto.auth_service.service.impl;

import com.crypto.auth_service.dto.LoginRequest;
import com.crypto.auth_service.dto.RegisterRequest;
import com.crypto.auth_service.entity.User;
import com.crypto.auth_service.exception.ResourceAlreadyExistsException;
import com.crypto.auth_service.repository.UserRepository;
import com.crypto.auth_service.role_enum.Role;
import com.crypto.auth_service.security.JwtUtil;
import com.crypto.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest request) {
        Role role;

        if (request.getRole() == null || request.getRole().isBlank()) {
            role = Role.USER;
        } else {
            role = Role.valueOf(request.getRole().toUpperCase());

            if (role == Role.ADMIN) {
                throw new RuntimeException("Admin registration not allowed");
            }
        }

        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Email already registered");
                });

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}