package com.crypto.portfolio_service.controller;

import com.crypto.portfolio_service.dto.AddAssetRequest;
import com.crypto.portfolio_service.dto.PortfolioResponse;
import com.crypto.portfolio_service.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// Base URL for all portfolio related APIs
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public PortfolioResponse getPortfolio(
            @RequestHeader("X-User-Email")
            String userEmail
    ) {

        return portfolioService.getPortfolio(
                userEmail
        );
    }
}