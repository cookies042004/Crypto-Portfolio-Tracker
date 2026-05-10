package com.crypto.holding_service.exception;

public class DuplicatePortfolioException extends RuntimeException {

    public DuplicatePortfolioException(String message) {
        super(message);
    }
}