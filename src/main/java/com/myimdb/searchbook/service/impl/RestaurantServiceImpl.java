package com.myimdb.searchbook.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.myimdb.searchbook.dto.CreateMenuItemRequest;
import com.myimdb.searchbook.dto.CreateRestaurantRequest;
import com.myimdb.searchbook.dto.MenuItemResponse;
import com.myimdb.searchbook.dto.RestaurantResponse;
import com.myimdb.searchbook.entity.MenuItem;
import com.myimdb.searchbook.entity.Restaurant;
import com.myimdb.searchbook.exception.BadRequestException;
import com.myimdb.searchbook.exception.ResourceNotFoundException;
import com.myimdb.searchbook.repository.MenuItemRepository;
import com.myimdb.searchbook.repository.RestaurantRepository;
import com.myimdb.searchbook.service.RestaurantService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        if (isBlank(request.getName()) || isBlank(request.getCategory()) || request.getDeliveryFee() == null) {
            throw new BadRequestException("Restaurant name, category, and delivery fee are required");
        }

        Restaurant restaurant = new Restaurant(
                request.getName(),
                request.getCategory(),
                request.getDeliveryFee());

        return toRestaurantResponse(restaurantRepository.save(restaurant));
    }

    @Override
    public MenuItemResponse addMenuItem(Long restaurantId, CreateMenuItemRequest request) {
        Restaurant restaurant = getRestaurantEntity(restaurantId);
        if (isBlank(request.getName()) || request.getPrice() == null) {
            throw new BadRequestException("Menu item name and price are required");
        }

        MenuItem menuItem = new MenuItem(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getAvailable() == null ? Boolean.TRUE : request.getAvailable(),
                restaurant);

        return toMenuItemResponse(menuItemRepository.save(menuItem));
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        List<RestaurantResponse> responses = new ArrayList<>();
        Set<String> categorySet = new TreeSet<>();

        for (Restaurant restaurant : restaurantRepository.findAll()) {
            responses.add(toRestaurantResponse(restaurant));
            categorySet.add(restaurant.getCategory());
        }

        Consumer<List<RestaurantResponse>> sorter = items ->
                items.sort((left, right) -> left.getName().compareToIgnoreCase(right.getName()));
        sorter.accept(responses);

        return responses;
    }

    @Override
    public List<MenuItemResponse> getMenuByRestaurant(Long restaurantId) {
        getRestaurantEntity(restaurantId);
        List<MenuItemResponse> responses = new ArrayList<>();
        for (MenuItem menuItem : menuItemRepository.findByRestaurantId(restaurantId)) {
            responses.add(toMenuItemResponse(menuItem));
        }
        return responses;
    }

    @Override
    public List<MenuItemResponse> searchMenuItems(String keyword) {
        return applySearch(keyword, null);
    }

    @Override
    public List<MenuItemResponse> searchMenuItems(String keyword, Long restaurantId) {
        getRestaurantEntity(restaurantId);
        return applySearch(keyword, restaurantId);
    }

    @Override
    public Restaurant getRestaurantEntity(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));
    }

    @Override
    public MenuItem getMenuItemEntity(Long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id " + menuItemId));
    }

    private List<MenuItemResponse> applySearch(String keyword, Long restaurantId) {
        String searchKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        Predicate<MenuItem> keywordFilter = item ->
                item.getName().toLowerCase().contains(searchKeyword)
                        || (item.getDescription() != null
                        && item.getDescription().toLowerCase().contains(searchKeyword));
        Predicate<MenuItem> restaurantFilter = item ->
                restaurantId == null || item.getRestaurant().getId().equals(restaurantId);
        Function<MenuItem, MenuItemResponse> mapper = this::toMenuItemResponse;

        List<MenuItemResponse> responses = new ArrayList<>();
        for (MenuItem menuItem : menuItemRepository.findAll()) {
            if (keywordFilter.and(restaurantFilter).test(menuItem)) {
                responses.add(mapper.apply(menuItem));
            }
        }
        return responses;
    }

    private RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .category(restaurant.getCategory())
                .deliveryFee(restaurant.getDeliveryFee())
                .open(restaurant.getOpen())
                .build();
    }

    private MenuItemResponse toMenuItemResponse(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .restaurantId(menuItem.getRestaurant().getId())
                .restaurantName(menuItem.getRestaurant().getName())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .available(menuItem.getAvailable())
                .build();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
