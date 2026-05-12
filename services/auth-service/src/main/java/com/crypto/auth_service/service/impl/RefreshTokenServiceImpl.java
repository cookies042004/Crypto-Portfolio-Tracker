package com.crypto.auth_service.service.impl;

import com.crypto.auth_service.entity.RefreshToken;
import com.crypto.auth_service.entity.User;
import com.crypto.auth_service.exception.ResourceNotFoundException;
import com.crypto.auth_service.repository.RefreshTokenRepository;
import com.crypto.auth_service.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Creates new refresh token for user
     */
    @Override
    public RefreshToken createRefreshToken(User user) {

        // Remove old refresh token if exists
        refreshTokenRepository.deleteByUser(user);

        // Generate random UUID token
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)

                // Refresh token valid for 7 days
                .expiryDate(
                        LocalDateTime.now().plusDays(7)
                )

                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Validates refresh token
     */
    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Invalid refresh token")
                        );

        // Check token expiry
        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new ResourceNotFoundException("Refresh token expired");
        }

        return refreshToken;
    }
}