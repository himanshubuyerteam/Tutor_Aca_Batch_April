package com.myimdb.searchbook.service;

import java.util.List;

import com.myimdb.searchbook.dto.OrderResponse;
import com.myimdb.searchbook.dto.OrderStatusUpdateRequest;
import com.myimdb.searchbook.dto.PlaceOrderRequest;

public interface OrderService {
    OrderResponse placeOrder(PlaceOrderRequest request);
    OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrdersByCustomer(Long customerId);
}
