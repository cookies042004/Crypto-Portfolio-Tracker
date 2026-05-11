package com.crypto.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange name used to publish alert messages
    public static final String ALERT_EXCHANGE =
            "alert.exchange";

    // Queue where triggered alerts will be stored
    public static final String ALERT_QUEUE =
            "alert.triggered.queue";

    // Routing key used to send messages to the queue
    public static final String ALERT_ROUTING_KEY =
            "alert.triggered";

    // Creates a Topic Exchange
    // Exchange is responsible for routing messages
    @Bean
    public TopicExchange alertExchange() {

        return new TopicExchange(
                ALERT_EXCHANGE
        );
    }

    // Creates the queue which will receive alert messages
    @Bean
    public Queue alertQueue() {

        return new Queue(
                ALERT_QUEUE
        );
    }

    // Binds queue with exchange using routing key
    // So messages with matching routing key reach this queue
    @Bean
    public Binding alertBinding() {

        return BindingBuilder
                .bind(alertQueue())
                .to(alertExchange())
                .with(ALERT_ROUTING_KEY);
    }
}