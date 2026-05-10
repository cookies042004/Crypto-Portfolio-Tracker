package com.crypto.portfolio_service.repository;

import com.crypto.portfolio_service.entity.Portfolio;
import com.crypto.portfolio_service.entity.PortfolioAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset, Long> {

    List<PortfolioAsset> findByPortfolio(Portfolio portfolio);

    Optional<PortfolioAsset> findByPortfolioAndSymbol(
            Portfolio portfolio,
            String symbol
    );
}
