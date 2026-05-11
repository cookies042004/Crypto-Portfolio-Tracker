package com.crypto.portfolio_service.client;

import com.crypto.portfolio_service.dto.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "price-service")
public interface PriceClient {

    @GetMapping("/api/prices/{symbol}")
    PriceResponse getPrice(
            @PathVariable String symbol
    );
}
