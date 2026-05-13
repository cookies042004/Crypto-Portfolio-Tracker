package com.crypto.price_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponse {

    private String coin;

    private BigDecimal priceUsd;

    private BigDecimal changePercent24h;

    private LocalDateTime fetchedAt;
}