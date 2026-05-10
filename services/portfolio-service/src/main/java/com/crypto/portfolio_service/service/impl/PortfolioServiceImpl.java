package com.crypto.portfolio_service.service.impl;

import com.crypto.portfolio_service.dto.AddAssetRequest;
import com.crypto.portfolio_service.dto.AssetResponse;
import com.crypto.portfolio_service.dto.PortfolioResponse;
import com.crypto.portfolio_service.dto.PriceResponse;
import com.crypto.portfolio_service.entity.Portfolio;
import com.crypto.portfolio_service.entity.PortfolioAsset;
import com.crypto.portfolio_service.exception.AssetNotFoundException;
import com.crypto.portfolio_service.exception.DuplicatePortfolioException;
import com.crypto.portfolio_service.exception.PortfolioNotFoundException;
import com.crypto.portfolio_service.repository.PortfolioAssetRepository;
import com.crypto.portfolio_service.repository.PortfolioRepository;
import com.crypto.portfolio_service.service.PortfolioService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    // Handles portfolio related DB operations
    private final PortfolioRepository portfolioRepository;

    private final WebClient.Builder webClientBuilder;

    // Handles asset related DB operations
    private final PortfolioAssetRepository assetRepository;

    @Override
    public void createPortfolio(String userEmail) {

        // Checking if user already has a portfolio
        portfolioRepository.findByUserEmail(userEmail)
                .ifPresent(p -> {
                    throw new DuplicatePortfolioException("Portfolio already exists");
                });

        // Creating fresh portfolio for new user
        Portfolio portfolio = Portfolio.builder()
                .userEmail(userEmail)
                .build();

        // Saving portfolio into database
        portfolioRepository.save(portfolio);
    }

    @Override
    public void addAsset(String userEmail, AddAssetRequest request) {

        // Fetching portfolio of logged-in user
        Portfolio portfolio = portfolioRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));

        // Checking whether asset already exists in portfolio
        Optional<PortfolioAsset> existingAsset =
                assetRepository.findByPortfolioAndSymbol(
                        portfolio,
                        request.getSymbol().toUpperCase()
                );

        if(existingAsset.isPresent()) {

            // If asset already exists, just update quantity
            PortfolioAsset asset = existingAsset.get();

            asset.setQuantity(
                    asset.getQuantity() + request.getQuantity()
            );

            // Saving updated quantity
            assetRepository.save(asset);

        } else {

            // New asset entry for this portfolio
            PortfolioAsset asset = PortfolioAsset.builder()
                    .symbol(request.getSymbol().toUpperCase())
                    .quantity(request.getQuantity())
                    .portfolio(portfolio)
                    .build();

            // Persisting new asset into DB
            assetRepository.save(asset);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PortfolioResponse getPortfolio(String userEmail) {

        // Fetching portfolio using user email
        Portfolio portfolio = portfolioRepository.findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new PortfolioNotFoundException("Portfolio not found"));

        // Fetching all assets belonging to this portfolio
        List<AssetResponse> assets = assetRepository.findByPortfolio(portfolio)
                .stream()
                .map(asset -> {

                    // Calling price-service to get live crypto price
                    PriceResponse priceResponse = webClientBuilder.build()
                            .get()
                            .uri("lb://price-service/api/prices/" + asset.getSymbol())
                            .retrieve()
                            .bodyToMono(PriceResponse.class)
                            .block();

                    // Current market price of crypto
                    Double currentPrice = priceResponse.getPrice();

                    // Calculating total asset value
                    // Example -> 2 BTC * 65000
                    Double totalValue =
                            asset.getQuantity() * currentPrice;

                    // Preparing asset response object
                    return AssetResponse.builder()
                            .symbol(asset.getSymbol())
                            .quantity(asset.getQuantity())
                            .currentPrice(currentPrice)
                            .totalValue(totalValue)
                            .build();

                })
                .toList();

        Double totalPortfolioValue = assets.stream()
                .mapToDouble(AssetResponse::getTotalValue)
                .sum();

        // Returning complete portfolio response
        return PortfolioResponse.builder()
                .userEmail(userEmail)
                .assets(assets)
                .totalPortfolioValue(totalPortfolioValue)
                .build();
    }

    @Override
    public void removeAsset(String userEmail, String symbol) {

        // Finding portfolio of current user
        Portfolio portfolio = portfolioRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));

        // Finding asset which needs to be removed
        PortfolioAsset asset = assetRepository
                .findByPortfolioAndSymbol(
                        portfolio,
                        symbol.toUpperCase()
                )
                .orElseThrow(() -> new AssetNotFoundException("Asset not found"));

        // Removing asset from portfolio
        assetRepository.delete(asset);
    }
}
