package com.myimdb.searchbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponse {
    private Long menuItemId;
    private String menuItemName;
    private Integer quantity;
    private Double itemPrice;
}
