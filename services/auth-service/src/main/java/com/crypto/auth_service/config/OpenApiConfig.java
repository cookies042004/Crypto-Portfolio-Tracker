package com.crypto.auth_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * Configures Swagger/OpenAPI documentation.
     */
    @Bean
    public OpenAPI customOpenAPI() {

        // JWT security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT");

        return new OpenAPI()

                // API metadata
                .info(
                        new Info()
                                .title("Crypto Portfolio Tracker Auth API")
                                .version("1.0")
                                .description("Authentication and Authorization APIs")
                                .contact(new Contact()
                                                .name("Akhil Puri")
                                                .email("akhil@test.com")
                                )
                )

                // Add JWT security
                .schemaRequirement(
                        "Bearer Authentication",
                        securityScheme
                )

                // Apply security globally
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication")
                );
    }
}