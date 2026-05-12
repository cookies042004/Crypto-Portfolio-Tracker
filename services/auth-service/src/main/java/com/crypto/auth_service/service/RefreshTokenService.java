package com.crypto.auth_service.service;

import com.crypto.auth_service.entity.RefreshToken;
import com.crypto.auth_service.entity.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);
}
