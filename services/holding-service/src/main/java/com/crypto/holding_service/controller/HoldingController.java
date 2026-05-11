package com.crypto.holding_service.controller;

import com.crypto.holding_service.dto.HoldingRequest;
import com.crypto.holding_service.dto.HoldingResponse;
import com.crypto.holding_service.service.HoldingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
@RequiredArgsConstructor
public class HoldingController {

    private final HoldingService holdingService;

    @PostMapping
    public String addHolding(
            @RequestHeader("X-User-Email") String userEmail,

            @Valid
            @RequestBody HoldingRequest request
    ) {

        holdingService.addHolding(userEmail, request);

        return "Holding added successfully";
    }

    @GetMapping
    public List<HoldingResponse> getHoldings(
            @RequestHeader("X-User-Email") String userEmail
    ) {

        return holdingService.getHoldings(userEmail);
    }

    @PutMapping("/{symbol}")
    public String updateHolding(

            @RequestHeader("X-User-Email")
            String userEmail,

            @PathVariable
            String symbol,

            @RequestParam
            Double quantity
    ) {

        holdingService.updateHolding(
                userEmail,
                symbol,
                quantity
        );

        return "Holding updated successfully";
    }

    @DeleteMapping("/{symbol}")
    public String deleteHolding(

            @RequestHeader("X-User-Email")
            String userEmail,

            @PathVariable
            String symbol
    ) {

        holdingService.deleteHolding(
                userEmail,
                symbol
        );

        return "Holding deleted successfully";
    }
}