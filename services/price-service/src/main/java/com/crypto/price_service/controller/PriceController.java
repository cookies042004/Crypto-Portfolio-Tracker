package com.crypto.price_service.controller;

import com.crypto.price_service.dto.response.PriceResponse;
import com.crypto.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/{symbol}")
    public PriceResponse getPrice(
            @PathVariable String symbol
    ) {

        return priceService.getCurrentPrice(symbol);
    }

    @GetMapping
    public List<PriceResponse> getAllPrices() {

        return priceService.getAllPrices();
    }

    @GetMapping("/{symbol}/history")
    public List<PriceResponse> getPriceHistory(
            @PathVariable String symbol
    ) {

        return priceService.getPriceHistory(symbol);
    }

    @PostMapping("/admin/refresh")
    public String refreshPrices() {

        priceService.fetchAndUpdateAll();

        return "Prices refreshed successfully";
    }

    @DeleteMapping("/admin/cache")
    public String clearCache() {

        priceService.clearCache();

        return "Cache cleared successfully";
    }
}