package com.crypto.price_service.dto;

import lombok.Data;

@Data
public class CoinGeckoResponse {

    private String id;

    private String symbol;

    private String name;

    private Double current_price;

    private Double market_cap;
}