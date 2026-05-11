package com.crypto.holding_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingRequest {

    @NotBlank
    private String symbol;

    @Positive
    private Double quantity;
}