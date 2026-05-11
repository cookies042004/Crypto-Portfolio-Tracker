package com.crypto.price_service.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {

        // Creating reusable WebClient object
        // Used for calling external APIs/services
        return WebClient.builder().build();
    }
}
