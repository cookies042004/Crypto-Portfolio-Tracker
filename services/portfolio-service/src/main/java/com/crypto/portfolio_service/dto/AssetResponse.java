package com.crypto.portfolio_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetResponse {

    private String symbol;

    private Double quantity;
}