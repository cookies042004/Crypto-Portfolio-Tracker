package com.crypto.portfolio_service.entity;

import com.crypto.portfolio_service.entity.Portfolio;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio_assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
