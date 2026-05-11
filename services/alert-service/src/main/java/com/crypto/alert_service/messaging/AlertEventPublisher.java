package com.crypto.alert_service.messaging;

import com.crypto.alert_service.dto.AlertTriggeredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertEventPublisher {

    // RabbitTemplate helps to send messages to RabbitMQ
    private final RabbitTemplate rabbitTemplate;

    // Publishes alert event to RabbitMQ exchange
    public void publish(AlertTriggeredEvent event) {

        rabbitTemplate.convertAndSend(

                // Exchange name
                "alert.exchange",

                // Routing key
                "alert.triggered",

                // Message payload
                event
        );
    }
}