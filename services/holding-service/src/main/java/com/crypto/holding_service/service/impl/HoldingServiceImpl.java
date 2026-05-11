package com.crypto.holding_service.service.impl;

import com.crypto.holding_service.dto.HoldingRequest;
import com.crypto.holding_service.dto.HoldingResponse;
import com.crypto.holding_service.entity.Holding;
import com.crypto.holding_service.exception.AssetNotFoundException;
import com.crypto.holding_service.repository.HoldingRepository;
import com.crypto.holding_service.service.HoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoldingServiceImpl implements HoldingService {

    // Repository layer for DB operations
    private final HoldingRepository holdingRepository;

    @Override
    public void addHolding(
            String userEmail,
            HoldingRequest request
    ) {

        // Checking whether holding already exists for user + symbol
        Holding holding = holdingRepository
                .findByUserEmailAndSymbol(
                        userEmail,
                        request.getSymbol().toUpperCase()
                )
                .orElse(

                        // Creating new holding if not present
                        Holding.builder()
                                .userEmail(userEmail)
                                .symbol(request.getSymbol().toUpperCase())
                                .quantity(0.0)
                                .build()
                );

        // Increasing holding quantity
        // Example -> existing 2 BTC + new 1 BTC = 3 BTC
        holding.setQuantity(
                holding.getQuantity() + request.getQuantity()
        );

        // Saving updated holding into database
        holdingRepository.save(holding);
    }

    @Override
    public List<HoldingResponse> getHoldings(
            String userEmail
    ) {

        // Fetching all holdings of current user
        return holdingRepository
                .findByUserEmail(userEmail)
                .stream()

                // Converting entity into response DTO
                .map(holding -> HoldingResponse.builder()
                        .symbol(holding.getSymbol())
                        .quantity(holding.getQuantity())
                        .build())

                .toList();
    }

    @Override
    public void updateHolding(
            String userEmail,
            String symbol,
            Double quantity
    ) {

        Holding holding = holdingRepository
                .findByUserEmailAndSymbol(
                        userEmail,
                        symbol.toUpperCase()
                )
                .orElseThrow(() ->
                        new AssetNotFoundException("Holding not found"));

        holding.setQuantity(quantity);

        holdingRepository.save(holding);
    }

    @Override
    public void deleteHolding(
            String userEmail,
            String symbol
    ) {

        Holding holding = holdingRepository
                .findByUserEmailAndSymbol(
                        userEmail,
                        symbol.toUpperCase()
                )
                .orElseThrow(() ->
                        new AssetNotFoundException("Holding not found"));

        holdingRepository.delete(holding);
    }
}