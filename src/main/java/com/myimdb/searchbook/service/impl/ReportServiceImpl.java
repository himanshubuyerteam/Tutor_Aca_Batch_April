package com.myimdb.searchbook.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.myimdb.searchbook.dto.ReportResponse;
import com.myimdb.searchbook.entity.FoodOrder;
import com.myimdb.searchbook.repository.CustomerRepository;
import com.myimdb.searchbook.repository.FoodOrderRepository;
import com.myimdb.searchbook.repository.RestaurantRepository;
import com.myimdb.searchbook.service.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final FoodOrderRepository foodOrderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public ReportResponse generateSnapshot() {
        List<FoodOrder> orders = foodOrderRepository.findAll();
        ReportHolder reportHolder = new ReportHolder();

        Thread statusThread = new Thread(() -> populateStatusSummary(orders, reportHolder));
        Thread revenueThread = new Thread(() -> populateRevenueSummary(orders, reportHolder));

        statusThread.start();
        revenueThread.start();

        try {
            statusThread.join();
            revenueThread.join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }

        return ReportResponse.builder()
                .orderCountByStatus(reportHolder.getOrderCountByStatus())
                .revenueByRestaurant(reportHolder.getRevenueByRestaurant())
                .totalCustomers(customerRepository.count())
                .totalRestaurants(restaurantRepository.count())
                .build();
    }

    private void populateStatusSummary(List<FoodOrder> orders, ReportHolder reportHolder) {
        Map<String, Long> statusSummary = new LinkedHashMap<>();
        Consumer<FoodOrder> orderConsumer = order -> {
            String key = order.getStatus().name();
            Long currentCount = statusSummary.getOrDefault(key, 0L);
            statusSummary.put(key, currentCount + 1);
        };

        for (FoodOrder order : orders) {
            orderConsumer.accept(order);
        }

        reportHolder.setOrderCountByStatus(statusSummary);
    }

    private void populateRevenueSummary(List<FoodOrder> orders, ReportHolder reportHolder) {
        Map<String, Double> revenueSummary = new LinkedHashMap<>();
        for (FoodOrder order : orders) {
            String restaurantName = order.getRestaurant().getName();
            Double currentAmount = revenueSummary.getOrDefault(restaurantName, 0.0);
            revenueSummary.put(restaurantName, currentAmount + order.getTotalAmount());
        }
        reportHolder.setRevenueByRestaurant(revenueSummary);
    }

    private static class ReportHolder {
        private Map<String, Long> orderCountByStatus = new LinkedHashMap<>();
        private Map<String, Double> revenueByRestaurant = new LinkedHashMap<>();

        public Map<String, Long> getOrderCountByStatus() {
            return orderCountByStatus;
        }

        public void setOrderCountByStatus(Map<String, Long> orderCountByStatus) {
            this.orderCountByStatus = orderCountByStatus;
        }

        public Map<String, Double> getRevenueByRestaurant() {
            return revenueByRestaurant;
        }

        public void setRevenueByRestaurant(Map<String, Double> revenueByRestaurant) {
            this.revenueByRestaurant = revenueByRestaurant;
        }
    }
}
