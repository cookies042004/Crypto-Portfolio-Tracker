package com.crypto.alert_service.service.impl;

import com.crypto.alert_service.dto.CreateAlertRequest;
import com.crypto.alert_service.entity.PriceAlert;
import com.crypto.alert_service.repository.PriceAlertRepository;
import com.crypto.alert_service.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    // Repository used for alert related DB operations
    private final PriceAlertRepository repository;

    @Override
    public void createAlert(
            String userEmail,
            CreateAlertRequest request
    ) {

        // Creating new price alert object
        PriceAlert alert = PriceAlert.builder()

                // Email of logged-in user
                .userEmail(userEmail)

                // Crypto symbol like BTC, ETH
                .symbol(request.getSymbol().toUpperCase())

                // Target price set by user
                .targetPrice(request.getTargetPrice())

                // ABOVE or BELOW condition
                .conditionType(request.getConditionType())

                // Initially alert is not triggered
                .triggered(false)

                .build();

        // Saving alert into database
        repository.save(alert);
    }
}