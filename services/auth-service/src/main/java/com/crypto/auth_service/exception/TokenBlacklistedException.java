package com.crypto.auth_service.exception;

public class TokenBlacklistedException extends RuntimeException {

    public TokenBlacklistedException(String message) {
        super(message);
    }
}