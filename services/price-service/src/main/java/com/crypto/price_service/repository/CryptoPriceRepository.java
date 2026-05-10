package com.crypto.price_service.repository;

import com.crypto.price_service.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    Optional<CryptoPrice> findByCoinId(String coinId);
}