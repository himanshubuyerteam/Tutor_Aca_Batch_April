package com.myimdb.searchbook.controller;

import java.util.List;

import com.myimdb.searchbook.dto.CreateMenuItemRequest;
import com.myimdb.searchbook.dto.CreateRestaurantRequest;
import com.myimdb.searchbook.dto.MenuItemResponse;
import com.myimdb.searchbook.dto.RestaurantResponse;
import com.myimdb.searchbook.service.RestaurantService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest request) {
        return restaurantService.createRestaurant(request);
    }

    @PostMapping("/{restaurantId}/menu-items")
    public MenuItemResponse addMenuItem(@PathVariable Long restaurantId, @RequestBody CreateMenuItemRequest request) {
        return restaurantService.addMenuItem(restaurantId, request);
    }

    @GetMapping
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}/menu-items")
    public List<MenuItemResponse> getMenuByRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.getMenuByRestaurant(restaurantId);
    }

    @GetMapping("/menu-items/search")
    public List<MenuItemResponse> searchMenuItems(
            @RequestParam String keyword,
            @RequestParam(required = false) Long restaurantId) {
        if (restaurantId == null) {
            return restaurantService.searchMenuItems(keyword);
        }
        return restaurantService.searchMenuItems(keyword, restaurantId);
    }
}
