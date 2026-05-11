package com.crypto.alert_service.repository;

import com.crypto.alert_service.entity.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

    List<PriceAlert> findByTriggeredFalse();
}