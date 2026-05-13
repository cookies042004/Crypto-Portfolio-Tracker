package com.crypto.price_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coin_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CoinSymbol coin;

    @Column(precision = 18, scale = 8)
    private BigDecimal priceUsd;

    @Column(precision = 10, scale = 4)
    private BigDecimal changePercent24h;

    private LocalDateTime fetchedAt;
}