package com.crypto.alert_service.controller;

import com.crypto.alert_service.dto.CreateAlertRequest;
import com.crypto.alert_service.service.AlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    public String createAlert(

            @RequestHeader("X-User-Email")
            String userEmail,

            @Valid
            @RequestBody
            CreateAlertRequest request
    ) {

        alertService.createAlert(
                userEmail,
                request
        );

        return "Alert created successfully";
    }
}