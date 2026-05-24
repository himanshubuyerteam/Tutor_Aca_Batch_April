package com.myimdb.searchbook.repository;

import java.util.List;

import com.myimdb.searchbook.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByCustomerId(Long customerId);
}
