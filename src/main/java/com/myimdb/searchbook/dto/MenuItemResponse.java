package com.myimdb.searchbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuItemResponse {
    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
}
