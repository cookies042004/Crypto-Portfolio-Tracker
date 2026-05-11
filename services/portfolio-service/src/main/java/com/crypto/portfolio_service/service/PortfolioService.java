package com.crypto.portfolio_service.service;

import com.crypto.portfolio_service.dto.AddAssetRequest;
import com.crypto.portfolio_service.dto.PortfolioResponse;

public interface PortfolioService {
    PortfolioResponse getPortfolio(String userEmail);
}
