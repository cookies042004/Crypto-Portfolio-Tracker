package com.crypto.alert_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponse {

    private String symbol;

    private Double price;
}