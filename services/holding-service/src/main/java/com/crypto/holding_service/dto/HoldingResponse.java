package com.crypto.holding_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingResponse {

    private String symbol;

    private Double quantity;
}