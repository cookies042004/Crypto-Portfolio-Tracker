package com.crypto.auth_service.service.impl;

import com.crypto.auth_service.service.TokenBlacklistService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void blacklistToken(String token) {

        // Store token in Redis blacklist for 15 minutes
        redisTemplate.opsForValue().set(
                token,
                "blacklisted",
                java.time.Duration.ofMinutes(15)
        );
    }

    @Override
    public boolean isBlacklisted(String token) {

        return redisTemplate.hasKey(token);
    }
}