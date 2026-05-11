package com.crypto.notification_service.controller;

import com.crypto.notification_service.dto.NotificationRequest;
import com.crypto.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public String sendNotification(
            @RequestBody
            NotificationRequest request
    ) {

        notificationService.sendNotification(
                request
        );

        return "Notification sent";
    }
}