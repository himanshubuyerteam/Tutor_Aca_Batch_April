package com.myimdb.searchbook.service;

import com.myimdb.searchbook.entity.FoodOrder;

public interface NotificationService {
    void sendOrderPlacedMessage(FoodOrder order);
    void sendStatusChangeMessage(FoodOrder order);
}
