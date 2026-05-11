package com.crypto.holding_service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;
}