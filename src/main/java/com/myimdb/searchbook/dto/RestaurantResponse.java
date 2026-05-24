package com.myimdb.searchbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String category;
    private Double deliveryFee;
    private Boolean open;
}
