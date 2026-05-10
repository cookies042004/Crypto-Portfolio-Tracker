package com.crypto.portfolio_service.repository;

import com.crypto.portfolio_service.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUserEmail(String userEmail);
}
