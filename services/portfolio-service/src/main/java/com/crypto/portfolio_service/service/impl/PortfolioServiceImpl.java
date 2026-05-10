package com.crypto.portfolio_service.service.impl;

import com.crypto.portfolio_service.client.HoldingClient;
import com.crypto.portfolio_service.client.PriceClient;
import com.crypto.portfolio_service.dto.*;
import com.crypto.portfolio_service.service.PortfolioService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    private final HoldingClient holdingClient;

    private final PriceClient priceClient;


    @Override
    public PortfolioResponse getPortfolio(String userEmail) {

        List<HoldingResponse> holdings =
                holdingClient.getHoldings(userEmail);

        List<AssetResponse> assets = holdings.stream()
                .map(holding -> {

                    PriceResponse priceResponse =
                            priceClient.getPrice(
                                    holding.getSymbol()
                            );

                    Double currentPrice =
                            priceResponse.getPrice();

                    Double totalValue =
                            holding.getQuantity()
                                    * currentPrice;

                    return AssetResponse.builder()
                            .symbol(holding.getSymbol())
                            .quantity(holding.getQuantity())
                            .currentPrice(currentPrice)
                            .totalValue(totalValue)
                            .build();

                })
                .toList();

        Double totalPortfolioValue =
                assets.stream()
                        .mapToDouble(
                                AssetResponse::getTotalValue
                        )
                        .sum();

        return PortfolioResponse.builder()
                .userEmail(userEmail)
                .assets(assets)
                .totalPortfolioValue(totalPortfolioValue)
                .build();
    }
}