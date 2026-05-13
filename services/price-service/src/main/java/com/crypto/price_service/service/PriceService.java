package com.crypto.price_service.service;

import com.crypto.price_service.dto.response.PriceResponse;

import java.util.List;

public interface PriceService {

    PriceResponse getCurrentPrice(String coinSymbol);

    List<PriceResponse> getAllPrices();

    List<PriceResponse> getPriceHistory(String coinSymbol);

    void fetchAndUpdateAll();

    void clearCache();
}