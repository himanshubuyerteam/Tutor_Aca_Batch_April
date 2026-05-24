package com.myimdb.searchbook.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest {
    private Long customerId;
    private Long restaurantId;
    private String notes;
    private List<PlaceOrderItemRequest> items;
}
