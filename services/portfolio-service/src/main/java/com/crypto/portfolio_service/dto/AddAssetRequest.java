package com.crypto.portfolio_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddAssetRequest {

    @NotBlank
    private String symbol;

    @Positive
    private Double quantity;
}
