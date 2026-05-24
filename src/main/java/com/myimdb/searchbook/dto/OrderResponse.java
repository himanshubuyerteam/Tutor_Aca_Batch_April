package com.myimdb.searchbook.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private String customerName;
    private String restaurantName;
    private String status;
    private String statusDescription;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String notes;
    private List<OrderItemResponse> items;
}
