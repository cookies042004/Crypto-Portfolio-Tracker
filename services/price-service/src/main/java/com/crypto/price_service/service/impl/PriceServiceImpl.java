package com.crypto.price_service.service.impl;

import com.crypto.price_service.dto.PriceResponse;
import com.crypto.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    // WebClient used for calling external APIs
    private final WebClient webClient;

    @Override
    public Mono<PriceResponse> getPrice(String symbol) {

        // Converting crypto symbol into CoinGecko coin id
        String coinId = mapSymbolToCoinId(symbol);

        // Calling CoinGecko API to fetch live crypto price
        return webClient.get()
                .uri("https://api.coingecko.com/api/v3/simple/price?ids="
                        + coinId
                        + "&vs_currencies=usd")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {

                    Map coinData = (Map) response.get(coinId);

                    Double price =
                            ((Number) coinData.get("usd")).doubleValue();

                    return PriceResponse.builder()
                            .symbol(symbol.toUpperCase())
                            .price(price)
                            .build();
                });
    }

    // Mapping user crypto symbol to CoinGecko supported id
    private String mapSymbolToCoinId(String symbol) {

        return switch (symbol.toUpperCase()) {

            // BTC -> bitcoin
            case "BTC" -> "bitcoin";

            // ETH -> ethereum
            case "ETH" -> "ethereum";

            // SOL -> solana
            case "SOL" -> "solana";

            // If coin is unsupported
            default -> throw new RuntimeException("Unsupported coin");
        };
    }
}
