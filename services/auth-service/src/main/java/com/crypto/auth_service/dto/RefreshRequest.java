package com.crypto.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {

    private String refreshToken;
}