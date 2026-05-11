package com.crypto.alert_service.dto;

import com.crypto.alert_service.enums.ConditionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAlertRequest {

    @NotBlank
    private String symbol;

    @NotNull
    @Positive
    private Double targetPrice;

    @NotNull
    private ConditionType conditionType;
}