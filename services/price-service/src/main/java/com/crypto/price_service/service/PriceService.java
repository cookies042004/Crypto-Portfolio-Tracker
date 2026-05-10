package com.crypto.price_service.service;

import com.crypto.price_service.dto.CoinGeckoResponse;
import com.crypto.price_service.entity.CryptoPrice;
import com.crypto.price_service.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final RestTemplate restTemplate;
    private final CryptoPriceRepository repository;

    private static final String COINGECKO_URL =
            "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd";

    public List<CryptoPrice> fetchAndSavePrices() {

        CoinGeckoResponse[] response = restTemplate.getForObject(
                COINGECKO_URL,
                CoinGeckoResponse[].class
        );

        if (response == null) {
            return List.of();
        }

        List<CryptoPrice> prices = Arrays.stream(response)
                .map(coin -> {

                    CryptoPrice cryptoPrice = repository
                            .findByCoinId(coin.getId())
                            .orElse(new CryptoPrice());

                    cryptoPrice.setCoinId(coin.getId());
                    cryptoPrice.setSymbol(coin.getSymbol());
                    cryptoPrice.setName(coin.getName());
                    cryptoPrice.setCurrentPrice(coin.getCurrent_price());
                    cryptoPrice.setMarketCap(coin.getMarket_cap());
                    cryptoPrice.setLastUpdated(LocalDateTime.now());

                    return repository.save(cryptoPrice);
                })
                .toList();

        return prices;
    }

    public List<CryptoPrice> getAllPrices() {
        return repository.findAll();
    }
}