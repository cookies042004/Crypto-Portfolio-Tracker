package com.crypto.alert_service.service;

import com.crypto.alert_service.dto.CreateAlertRequest;

public interface AlertService {

    void createAlert(
            String userEmail,
            CreateAlertRequest request
    );
}