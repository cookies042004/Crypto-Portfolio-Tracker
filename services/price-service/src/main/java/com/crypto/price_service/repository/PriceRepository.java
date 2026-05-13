package com.crypto.price_service.repository;

import com.crypto.price_service.entity.CoinPrice;
import com.crypto.price_service.entity.CoinSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface PriceRepository extends JpaRepository<CoinPrice, Long> {

    Optional<CoinPrice> findTopByCoinOrderByFetchedAtDesc(CoinSymbol coin);
    List<CoinPrice> findByCoinOrderByFetchedAtDesc(CoinSymbol coin);
}