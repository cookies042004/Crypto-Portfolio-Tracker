package com.crypto.portfolio_service.service;

import com.crypto.portfolio_service.dto.AddAssetRequest;
import com.crypto.portfolio_service.dto.PortfolioResponse;

public interface PortfolioService {

    void createPortfolio(String userEmail);

    void addAsset(String userEmail, AddAssetRequest request);

    PortfolioResponse getPortfolio(String userEmail);

    void removeAsset(String userEmail, String symbol);
}
