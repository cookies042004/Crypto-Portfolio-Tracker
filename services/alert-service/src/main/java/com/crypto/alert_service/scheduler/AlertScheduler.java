package com.crypto.alert_service.scheduler;

import com.crypto.alert_service.client.NotificationClient;
import com.crypto.alert_service.client.PriceClient;
import com.crypto.alert_service.dto.AlertTriggeredEvent;
import com.crypto.alert_service.dto.NotificationRequest;
import com.crypto.alert_service.dto.PriceResponse;
import com.crypto.alert_service.entity.PriceAlert;
import com.crypto.alert_service.enums.ConditionType;
import com.crypto.alert_service.messaging.AlertEventPublisher;
import com.crypto.alert_service.repository.PriceAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertScheduler {

    // Repository for fetching and updating alerts
    private final PriceAlertRepository repository;

    // Feign/Web client for calling price-service
    private final PriceClient priceClient;

    private final AlertEventPublisher alertEventPublisher;

    /**
     * Scheduler runs every 60 seconds
     * and checks all pending alerts.
     */
    @Scheduled(fixedRate = 60000)
    public void checkAlerts() {

        log.info("Checking crypto price alerts...");

        // Fetching only alerts which are not triggered yet
        List<PriceAlert> alerts =
                repository.findByTriggeredFalse();

        // Looping through all active alerts
        for (PriceAlert alert : alerts) {

            // Calling price-service for live crypto price
            PriceResponse response =
                    priceClient.getPrice(
                            alert.getSymbol()
                    );

            // Current market price
            Double currentPrice =
                    response.getPrice();

            boolean shouldTrigger = false;

            // Condition for ABOVE target price
            if (alert.getConditionType()
                    == ConditionType.ABOVE) {

                shouldTrigger =
                        currentPrice >= alert.getTargetPrice();
            }

            // Condition for BELOW target price
            if (alert.getConditionType()
                    == ConditionType.BELOW) {

                shouldTrigger =
                        currentPrice <= alert.getTargetPrice();
            }

            // If alert condition matches
            if (shouldTrigger) {

                // Preparing notification message
                String message =
                        String.format(
                                "%s crossed target price %.2f. Current price: %.2f",
                                alert.getSymbol(),
                                alert.getTargetPrice(),
                                currentPrice
                        );

                // Sending
                alertEventPublisher.publish(

                        AlertTriggeredEvent.builder()
                                .userEmail(
                                        alert.getUserEmail()
                                )
                                .symbol(alert.getSymbol())
                                .targetPrice(
                                        alert.getTargetPrice()
                                )
                                .currentPrice(currentPrice)
                                .conditionType(
                                        alert.getConditionType().name()
                                )
                                .build()
                );

                // Marking alert as triggered
                // so it doesn't notify again
                alert.setTriggered(true);

                // Updating DB
                repository.save(alert);

                log.info(
                        "Alert triggered for user {}",
                        alert.getUserEmail()
                );
            }
        }
    }
}