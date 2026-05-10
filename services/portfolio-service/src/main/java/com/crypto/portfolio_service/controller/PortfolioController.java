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

    // Service layer dependency
    private final PortfolioService portfolioService;

    // API to create new portfolio for user
    @PostMapping
    public ResponseEntity<String> createPortfolio(
            @RequestHeader("X-User-Email") String userEmail
    ) {

        // Delegating portfolio creation to service layer
        portfolioService.createPortfolio(userEmail);

        return ResponseEntity.ok("Portfolio created successfully");
    }

    // API to add crypto asset into portfolio
    @PostMapping("/assets")
    public ResponseEntity<String> addAsset(
            @RequestHeader("X-User-Email") String userEmail,
            @Valid @RequestBody AddAssetRequest request
    ) {

        // Adding asset for current user
        portfolioService.addAsset(userEmail, request);

        return ResponseEntity.ok("Asset added successfully");
    }

    // API to fetch complete portfolio details
    @GetMapping
    public ResponseEntity<PortfolioResponse> getPortfolio(
            @RequestHeader("X-User-Email") String userEmail
    ) {

        // Returning portfolio response
        return ResponseEntity.ok(
                portfolioService.getPortfolio(userEmail)
        );
    }

    // API to remove particular asset using symbol
    @DeleteMapping("/assets/{symbol}")
    public ResponseEntity<String> removeAsset(
            @RequestHeader("X-User-Email") String userEmail,
            @PathVariable String symbol
    ) {

        // Removing requested asset from portfolio
        portfolioService.removeAsset(userEmail, symbol);

        return ResponseEntity.ok("Asset removed successfully");
    }
}
