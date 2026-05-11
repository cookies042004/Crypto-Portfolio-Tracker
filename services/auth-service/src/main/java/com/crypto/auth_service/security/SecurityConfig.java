package com.crypto.auth_service.security;

import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Custom authentication filter responsible for extracting and validating
     * authentication data (e.g., JWT) from request headers.
     */
    private final HeaderAuthenticationFilter filter;

    /**
     * Defines the Spring Security filter chain.
     *
     * Responsibilities:
     * - Disables CSRF for stateless APIs
     * - Configures endpoint-level authorization rules
     * - Registers custom authentication filter
     * - Adjusts headers for H2 console access (dev only)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                /**
                 * CSRF is disabled because this is a stateless REST API.
                 * Tokens (JWT) are used instead of session-based authentication.
                 */
                .csrf(csrf -> csrf.disable())

                /**
                 * Defines authorization rules for incoming HTTP requests.
                 */
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
                                // Public endpoints:
                                // - Auth APIs (login/register)
                                // - H2 console (for development/testing)

                                .anyRequest().authenticated()
                        // All other endpoints require authentication
                )

                /**
                 * Adds custom authentication filter before Spring's default
                 * UsernamePasswordAuthenticationFilter.
                 *
                 * Ensures JWT is validated before request reaches secured endpoints.
                 */
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)

                /**
                 * Disables frame options to allow H2 console to render in browser.
                 * Required only for development (should not be enabled in production).
                 */
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}