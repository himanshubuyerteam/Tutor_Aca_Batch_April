package com.myimdb.searchbook.service;

import java.util.List;

import com.myimdb.searchbook.dto.CreateMenuItemRequest;
import com.myimdb.searchbook.dto.CreateRestaurantRequest;
import com.myimdb.searchbook.dto.MenuItemResponse;
import com.myimdb.searchbook.dto.RestaurantResponse;
import com.myimdb.searchbook.entity.MenuItem;
import com.myimdb.searchbook.entity.Restaurant;

public interface RestaurantService {
    RestaurantResponse createRestaurant(CreateRestaurantRequest request);
    MenuItemResponse addMenuItem(Long restaurantId, CreateMenuItemRequest request);
    List<RestaurantResponse> getAllRestaurants();
    List<MenuItemResponse> getMenuByRestaurant(Long restaurantId);
    List<MenuItemResponse> searchMenuItems(String keyword);
    List<MenuItemResponse> searchMenuItems(String keyword, Long restaurantId);
    Restaurant getRestaurantEntity(Long restaurantId);
    MenuItem getMenuItemEntity(Long menuItemId);
}
