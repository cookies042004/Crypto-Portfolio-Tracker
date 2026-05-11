package com.crypto.holding_service.service;

import com.crypto.holding_service.dto.HoldingRequest;
import com.crypto.holding_service.dto.HoldingResponse;

import java.util.List;

public interface HoldingService {

    void addHolding(
            String userEmail,
            HoldingRequest request
    );

    List<HoldingResponse> getHoldings(
            String userEmail
    );

    void updateHolding(
            String userEmail,
            String symbol,
            Double quantity
    );

    void deleteHolding(
            String userEmail,
            String symbol
    );
}