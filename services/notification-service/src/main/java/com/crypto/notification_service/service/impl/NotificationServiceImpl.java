package com.crypto.notification_service.service.impl;

import com.crypto.notification_service.dto.NotificationRequest;
import com.crypto.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(NotificationRequest request) {

        log.info("=================================");
        log.info("NOTIFICATION SENT");
        log.info("TO: {}", request.getUserEmail());
        log.info("MESSAGE: {}", request.getMessage());
        log.info("=================================");
    }
}