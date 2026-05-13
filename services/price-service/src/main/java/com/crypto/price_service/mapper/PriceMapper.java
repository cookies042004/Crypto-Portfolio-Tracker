package com.crypto.price_service.mapper;

import com.crypto.price_service.dto.response.PriceResponse;
import com.crypto.price_service.entity.CoinPrice;

public class PriceMapper {

    public static PriceResponse toResponse(CoinPrice coinPrice) {

        return PriceResponse.builder()
                .coin(coinPrice.getCoin().name())
                .priceUsd(coinPrice.getPriceUsd())
                .changePercent24h(coinPrice.getChangePercent24h())
                .fetchedAt(coinPrice.getFetchedAt())
                .build();
    }
}