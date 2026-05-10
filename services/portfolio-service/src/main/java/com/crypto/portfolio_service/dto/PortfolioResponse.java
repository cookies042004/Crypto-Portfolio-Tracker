package com.crypto.portfolio_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PortfolioResponse {

    private String userEmail;

    private List<AssetResponse> assets;
}
