package com.crypto.price_service.scheduler;

import com.crypto.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceFetchScheduler {

    private final PriceService priceService;

    @Scheduled(fixedRateString = "${price.fetch.interval-ms:300000}")
    public void fetchAndStore() {

        log.info("Fetching live prices from CoinGecko...");

        priceService.fetchAndUpdateAll();
    }
}