package com.myimdb.searchbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerRequest {
    private String name;
    private String email;
    private String phone;
    private String deliveryAddress;
}
