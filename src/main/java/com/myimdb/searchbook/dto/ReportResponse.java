package com.myimdb.searchbook.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {
    private Map<String, Long> orderCountByStatus;
    private Map<String, Double> revenueByRestaurant;
    private Long totalCustomers;
    private Long totalRestaurants;
}
