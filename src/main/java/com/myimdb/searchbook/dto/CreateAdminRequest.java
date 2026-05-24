package com.myimdb.searchbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdminRequest {
    private String name;
    private String email;
    private String phone;
    private String supportLevel;
}
