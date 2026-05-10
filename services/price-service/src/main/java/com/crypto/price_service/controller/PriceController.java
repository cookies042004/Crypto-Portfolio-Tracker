package com.crypto.price_service.controller;

import com.crypto.price_service.entity.CryptoPrice;
import com.crypto.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @PostMapping("/fetch")
    public List<CryptoPrice> fetchPrices() {
        return priceService.fetchAndSavePrices();
    }

    @GetMapping
    public List<CryptoPrice> getAllPrices() {
        return priceService.getAllPrices();
    }
}