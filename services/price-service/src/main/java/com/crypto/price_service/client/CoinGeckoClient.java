package com.crypto.price_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "coingecko",
        url = "https://api.coingecko.com/api/v3"
)
public interface CoinGeckoClient {

    @GetMapping("/simple/price")
    Map<String, Map<String, Double>> getPrice(
            @RequestParam("ids") String ids,
            @RequestParam("vs_currencies") String vsCurrencies,
            @RequestParam("include_24hr_change") boolean include24hrChange
    );
}