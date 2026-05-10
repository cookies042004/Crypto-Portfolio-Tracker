package com.crypto.price_service.service;

import com.crypto.price_service.dto.PriceResponse;
import reactor.core.publisher.Mono;

public interface PriceService {

    Mono<PriceResponse> getPrice(String symbol);
}
