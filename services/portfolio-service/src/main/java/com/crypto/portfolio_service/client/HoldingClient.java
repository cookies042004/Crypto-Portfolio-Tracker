package com.crypto.portfolio_service.client;

import com.crypto.portfolio_service.dto.HoldingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "holding-service")
public interface HoldingClient {

    @GetMapping("/api/holdings")
    List<HoldingResponse> getHoldings(
            @RequestHeader("X-User-Email") String userEmail
    );
}
