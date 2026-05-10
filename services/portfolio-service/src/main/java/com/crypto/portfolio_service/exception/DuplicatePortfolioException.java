package com.crypto.portfolio_service.exception;

public class DuplicatePortfolioException extends RuntimeException {

    public DuplicatePortfolioException(String message) {
        super(message);
    }
}