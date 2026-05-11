package com.crypto.notification_service.service;

import com.crypto.notification_service.dto.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest request);
}