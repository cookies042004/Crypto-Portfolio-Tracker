package com.crypto.price_service.service.impl;

import com.crypto.price_service.client.CoinGeckoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinGeckoFetchService {

    private final CoinGeckoClient coinGeckoClient;

    public Map<String, Map<String, Double>> fetchPrices() {

        coinGeckoClient.getPrice(
                "bitcoin,ethereum",
                "usd",
                true
        );

        return Map.of();
    }
}