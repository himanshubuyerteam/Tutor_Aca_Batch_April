package com.myimdb.searchbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String deliveryAddress;
    private Integer loyaltyPoints;
    private String userTypeDescription;
}
