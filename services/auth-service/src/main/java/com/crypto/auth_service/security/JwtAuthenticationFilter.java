package com.crypto.auth_service.security;

import com.crypto.auth_service.service.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    // Utility class used for JWT operations
    private final JwtUtil jwtUtil;

    // Service used to check whether token is blacklisted
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * Skip JWT validation for public APIs.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.startsWith("/api/auth") || path.startsWith("/h2-console");
    }

    /**
     * Main JWT authentication logic.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // If token not present, continue request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

        // Remove "Bearer " prefix
        String token = authHeader.substring(7);

        // Check whether token is blacklisted
        if (tokenBlacklistService.isBlacklisted(token)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("Token has been logged out");

            return;
        }

        try {
            // Extract email from JWT
            String email = jwtUtil.extractEmail(token);

            // Extract role from JWT
            String role = jwtUtil.extractRole(token);

            // Convert role into Spring Security authority
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            // Create authentication object
            var authentication = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authorities
                    );

            // Store authentication inside SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {

            // JWT invalid or expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("Invalid JWT token");

            return;
        }

        // Continue request processing
        filterChain.doFilter(request, response);
    }
}