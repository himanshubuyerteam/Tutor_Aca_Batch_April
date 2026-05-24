package com.myimdb.searchbook.repository;

import com.myimdb.searchbook.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
