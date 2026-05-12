package com.crypto.auth_service.service.impl;

import com.crypto.auth_service.dto.AuthResponse;
import com.crypto.auth_service.dto.LoginRequest;
import com.crypto.auth_service.dto.RefreshRequest;
import com.crypto.auth_service.dto.RegisterRequest;
import com.crypto.auth_service.entity.RefreshToken;
import com.crypto.auth_service.entity.User;
import com.crypto.auth_service.exception.InvalidCredentialsException;
import com.crypto.auth_service.exception.ResourceAlreadyExistsException;
import com.crypto.auth_service.exception.ResourceNotFoundException;
import com.crypto.auth_service.repository.UserRepository;
import com.crypto.auth_service.role_enum.Role;
import com.crypto.auth_service.security.JwtUtil;
import com.crypto.auth_service.service.AuthService;
import com.crypto.auth_service.service.RefreshTokenService;

import com.crypto.auth_service.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Repository used for database operations related to users
    private final UserRepository userRepository;

    // Used to encrypt user passwords before saving
    private final PasswordEncoder passwordEncoder;

    // Utility class for generating and validating JWT tokens
    private final JwtUtil jwtUtil;

    // Handles refresh token creation and validation
    private final RefreshTokenService refreshTokenService;

    // Responsible for authenticating user credentials
    private final AuthenticationManager authenticationManager;

    private final TokenBlacklistService tokenBlacklistService;

    /**
     * Registers a new user in the system
     */
    @Override
    public void register(RegisterRequest request) {

        Role role;

        // If role is not provided, assign USER role by default
        if (request.getRole() == null || request.getRole().isBlank()) {

            role = Role.USER;

        } else {

            // Convert incoming role string into enum
            role = Role.valueOf(request.getRole().toUpperCase());

            // Prevent direct ADMIN registration
            if (role == Role.ADMIN) {

                throw new ResourceNotFoundException("Admin registration not allowed");
            }
        }

        // Check if email is already registered
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {

                    throw new InvalidCredentialsException("Email already registered");
                });

        // Create new user object
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())

                // Encrypt password before storing in database
                .password(passwordEncoder.encode(request.getPassword()))

                .role(role)
                .build();

        // Save user into database
        userRepository.save(user);
    }

    /**
     * Authenticates user and returns access + refresh tokens
     */
    @Override
    public AuthResponse login(LoginRequest request) {

        // Authenticate user credentials
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Fetch user details from database
        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        // Generate JWT access token
        String accessToken = jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole().name()

        );

        // Generate and store refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // Return authentication response
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())

                // Access token expiry time in seconds
                .expiresIn(900L)

                .role(user.getRole().name())
                .build();
    }

    /**
     * Generates new access token using refresh token
     */
    @Override
    public AuthResponse refreshToken(
            RefreshRequest request
    ) {

        // Validate refresh token
        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        // Get associated user
        User user = refreshToken.getUser();

        // Generate new access token
        String accessToken = jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole().name()
        );

        // Return new JWT token
        return AuthResponse.builder()
                .accessToken(accessToken)

                // Returning same refresh token
                .refreshToken(refreshToken.getToken())

                .expiresIn(900L)

                .role(user.getRole().name())
                .build();
    }

    @Override
    public void logout(String token) {

        // Add token to Redis blacklist
        tokenBlacklistService.blacklistToken(token);
    }
}