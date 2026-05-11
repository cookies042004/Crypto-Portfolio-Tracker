package com.crypto.price_service.controller;

import com.crypto.price_service.dto.PriceResponse;
import com.crypto.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/{symbol}")
    public Mono<PriceResponse> getPrice(
            @PathVariable String symbol
    ) {

        return priceService.getPrice(symbol);
    }
}
