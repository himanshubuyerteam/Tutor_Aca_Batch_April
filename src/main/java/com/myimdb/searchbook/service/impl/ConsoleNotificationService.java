package com.myimdb.searchbook.service.impl;

import com.myimdb.searchbook.entity.FoodOrder;
import com.myimdb.searchbook.service.NotificationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsoleNotificationService implements NotificationService {

    @Override
    public void sendOrderPlacedMessage(FoodOrder order) {
        log.info("Notification: order {} placed for customer {}", order.getId(), order.getCustomer().getName());
    }

    @Override
    public void sendStatusChangeMessage(FoodOrder order) {
        log.info("Notification: order {} moved to status {}", order.getId(), order.getStatus());
    }
}
