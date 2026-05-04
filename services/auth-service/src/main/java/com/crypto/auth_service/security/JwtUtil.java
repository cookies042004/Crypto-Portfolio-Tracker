package com.crypto.auth_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Getter
public class JwtUtil {

    /**
     * Secret used for signing JWTs.
     * Must be a sufficiently long, high-entropy value (>= 256 bits for HS256).
     * Should be externalized (e.g., Config Server, environment variable) and never hardcoded.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Token expiration duration in milliseconds.
     * Defines how long a token remains valid after issuance.
     */
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Cached cryptographic key derived from the secret.
     * Initialized once during bean lifecycle to avoid repeated computation.
     */
    private Key key;

    /**
     * Initializes the signing key after dependency injection is complete.
     * Ensures the key is created only once and reused for all JWT operations.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a signed JWT for the given user identifier.
     *
     * @param email unique identifier of the user (stored as 'sub' claim)
     * @return compact JWT string
     *
     * Token structure:
     * Header  -> algorithm (HS256)
     * Payload -> subject (email), issuedAt, expiration
     * Signature -> HMAC using secret key
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Standard claim: identifies the principal (user)

                .setIssuedAt(new Date()) // Token creation timestamp

                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                // Absolute expiry time; token becomes invalid after this point

                .signWith(key, SignatureAlgorithm.HS256)
                // Signs token using HMAC SHA-256 to ensure integrity and authenticity

                .compact(); // Serializes JWT to URL-safe string
    }

    /**
     * Extracts the subject (email) from a valid JWT.
     *
     * @param token JWT string
     * @return email stored in the 'sub' claim
     */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Validates a JWT by verifying:
     * 1. Signature integrity (token not tampered)
     * 2. Expiration (token not expired)
     *
     * @param token JWT string
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // Parsing triggers validation checks
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // JwtException covers:
            // - ExpiredJwtException
            // - UnsupportedJwtException
            // - MalformedJwtException
            // - SignatureException
            return false;
        }
    }

    /**
     * Parses the JWT and extracts claims.
     * Centralized method to ensure consistent parsing and validation logic.
     *
     * @param token JWT string
     * @return Claims payload
     * @throws JwtException if token is invalid or expired
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Required to validate signature
                .build()
                .parseClaimsJws(token) // Parses and validates token
                .getBody(); // Returns claims (payload)
    }
}