package com.crypto.notification_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertTriggeredEvent {

    private String userEmail;

    private String symbol;

    private Double targetPrice;

    private Double currentPrice;

    private String conditionType;
}