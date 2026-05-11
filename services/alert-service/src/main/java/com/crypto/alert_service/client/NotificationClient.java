package com.crypto.alert_service.client;

import com.crypto.alert_service.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/notifications")
    String sendNotification(
            @RequestBody
            NotificationRequest request
    );
}