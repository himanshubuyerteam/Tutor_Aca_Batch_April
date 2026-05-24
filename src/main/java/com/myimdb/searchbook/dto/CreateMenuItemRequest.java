package com.myimdb.searchbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMenuItemRequest {
    private String name;
    private String description;
    private Double price;
    private Boolean available;
}
