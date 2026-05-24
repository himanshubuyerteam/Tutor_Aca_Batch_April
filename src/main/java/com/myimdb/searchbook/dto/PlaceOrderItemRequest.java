package com.myimdb.searchbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderItemRequest {
    private Long menuItemId;
    private Integer quantity;
}
