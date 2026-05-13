package com.crypto.price_service.service.impl;

import com.crypto.price_service.client.CoinGeckoClient;
import com.crypto.price_service.dto.response.PriceResponse;
import com.crypto.price_service.entity.CoinPrice;
import com.crypto.price_service.entity.CoinSymbol;
import com.crypto.price_service.mapper.PriceMapper;
import com.crypto.price_service.repository.PriceRepository;
import com.crypto.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final CoinGeckoClient coinGeckoClient;
    private final PriceRepository priceRepository;

    @Override
    @Cacheable(value = "coinPrices", key = "#coinSymbol")
    public PriceResponse getCurrentPrice(String coinSymbol) {

        CoinPrice coinPrice = priceRepository
                .findTopByCoinOrderByFetchedAtDesc(
                        CoinSymbol.valueOf(coinSymbol.toUpperCase())
                )
                .orElseThrow(() -> new RuntimeException("Coin not found"));

        return PriceMapper.toResponse(coinPrice);
    }

    @Override
    public List<PriceResponse> getAllPrices() {

        return priceRepository.findAll()
                .stream()
                .map(PriceMapper::toResponse)
                .toList();
    }

    @Override
    public List<PriceResponse> getPriceHistory(String coinSymbol) {

        return priceRepository
                .findByCoinOrderByFetchedAtDesc(
                        CoinSymbol.valueOf(coinSymbol.toUpperCase())
                )
                .stream()
                .map(PriceMapper::toResponse)
                .toList();
    }

    @Override
    public void fetchAndUpdateAll() {

        fetchAndSaveCoin("bitcoin", CoinSymbol.BTC);
        fetchAndSaveCoin("ethereum", CoinSymbol.ETH);
        fetchAndSaveCoin("solana", CoinSymbol.SOL);
    }

    private void fetchAndSaveCoin(String apiKey, CoinSymbol symbol) {

        Map<String, Map<String, Double>> response =
                coinGeckoClient.getPrice(
                        apiKey,
                        "usd",
                        true
                );

        Double price = response
                .get(apiKey)
                .get("usd");

        Double change24h = response
                .get(apiKey)
                .get("usd_24h_change");

        CoinPrice coinPrice = CoinPrice.builder()
                .coin(symbol)
                .priceUsd(BigDecimal.valueOf(price))
                .changePercent24h(BigDecimal.valueOf(change24h))
                .fetchedAt(LocalDateTime.now())
                .build();

        save(coinPrice);
    }

    @Override
    @CacheEvict(value = "coinPrices", allEntries = true)
    public void clearCache() {
        System.out.println("Price cache cleared");
    }

    public void save(CoinPrice entity) {
        priceRepository.save(entity);
    }
}