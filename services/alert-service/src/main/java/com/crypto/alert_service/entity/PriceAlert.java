package com.crypto.alert_service.entity;

import com.crypto.alert_service.enums.ConditionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String symbol;

    private Double targetPrice;

    @Enumerated(EnumType.STRING)
    private ConditionType conditionType;

    private Boolean triggered;
}