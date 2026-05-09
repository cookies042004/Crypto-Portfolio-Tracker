package com.crypto.api_gateway.filter;

import com.crypto.api_gateway.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Allow auth APIs
        if (path.contains("/api/auth")) {
            return chain.filter(exchange);
        }

        // Get Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Missing token");
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isValid(token)) {
            return onError(exchange, "Invalid token");
        }

        // Extract user info
        Claims claims  = jwtUtil.extractClaims(token);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        // Add header to downstream services
        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(r -> r.headers(h -> {
                    h.add("X-User-Email", email);
                    h.add("X-User-Role", role);
                }))
                .build();

        return chain.filter(modifiedExchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}