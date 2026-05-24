package com.myimdb.searchbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String supportLevel;
    private String userTypeDescription;
}
