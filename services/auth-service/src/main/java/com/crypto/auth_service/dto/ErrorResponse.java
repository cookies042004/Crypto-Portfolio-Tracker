package com.crypto.auth_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    // Time when error occurred
    private LocalDateTime timestamp;

    // HTTP status code
    private int status;

    // Error message
    private String message;

    // Request path
    private String path;
}