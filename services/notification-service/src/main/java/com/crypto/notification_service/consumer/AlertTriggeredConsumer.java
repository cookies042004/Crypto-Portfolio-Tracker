package com.crypto.notification_service.consumer;

import com.crypto.notification_service.dto.AlertTriggeredEvent;
import com.crypto.notification_service.dto.NotificationRequest;
import com.crypto.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertTriggeredConsumer {

    // Service used to send notifications
    private final NotificationService notificationService;

    // Listens for messages from RabbitMQ queue
    @RabbitListener(queues = "alert.triggered.queue")
    public void consume(AlertTriggeredEvent event) {

        // Log received alert event
        log.info(
                "Received alert event for user {}",
                event.getUserEmail()
        );

        // Create notification message
        String message = String.format(
                "%s crossed target price %.2f. Current price: %.2f",
                event.getSymbol(),
                event.getTargetPrice(),
                event.getCurrentPrice()
        );

        // Send notification to the user
        notificationService.sendNotification(

                NotificationRequest.builder()
                        .userEmail(event.getUserEmail())
                        .message(message)
                        .build()
        );
    }
}