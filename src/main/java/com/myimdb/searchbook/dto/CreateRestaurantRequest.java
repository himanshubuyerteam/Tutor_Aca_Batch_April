package com.myimdb.searchbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurantRequest {
    private String name;
    private String category;
    private Double deliveryFee;
}
