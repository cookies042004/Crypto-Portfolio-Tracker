package com.crypto.alert_service.client;

import com.crypto.alert_service.dto.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "price-service")
public interface PriceClient {

    @GetMapping("/api/prices/{symbol}")
    PriceResponse getPrice(
            @PathVariable String symbol
    );
}