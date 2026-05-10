package com.crypto.portfolio_service.exception;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String message) {
        super(message);
    }
}