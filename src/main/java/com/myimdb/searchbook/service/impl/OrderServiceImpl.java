package com.myimdb.searchbook.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import com.myimdb.searchbook.dto.OrderItemResponse;
import com.myimdb.searchbook.dto.OrderResponse;
import com.myimdb.searchbook.dto.OrderStatusUpdateRequest;
import com.myimdb.searchbook.dto.PlaceOrderItemRequest;
import com.myimdb.searchbook.dto.PlaceOrderRequest;
import com.myimdb.searchbook.entity.Customer;
import com.myimdb.searchbook.entity.FoodOrder;
import com.myimdb.searchbook.entity.MenuItem;
import com.myimdb.searchbook.entity.OrderItem;
import com.myimdb.searchbook.entity.Restaurant;
import com.myimdb.searchbook.enums.OrderStatus;
import com.myimdb.searchbook.exception.BadRequestException;
import com.myimdb.searchbook.exception.ResourceNotFoundException;
import com.myimdb.searchbook.repository.FoodOrderRepository;
import com.myimdb.searchbook.service.NotificationService;
import com.myimdb.searchbook.service.OrderService;
import com.myimdb.searchbook.service.RestaurantService;
import com.myimdb.searchbook.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final FoodOrderRepository foodOrderRepository;
    private final NotificationService notificationService;
    private final java.util.concurrent.ExecutorService orderExecutorService;

    private final Queue<Long> orderProcessingQueue = new LinkedList<>();

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        Customer customer = userService.getCustomerEntity(request.getCustomerId());
        Restaurant restaurant = restaurantService.getRestaurantEntity(request.getRestaurantId());
        if (!restaurant.getOpen()) {
            throw new BadRequestException("Restaurant is currently closed");
        }

        FoodOrder order = new FoodOrder(customer, restaurant, request.getNotes());
        List<OrderItem> orderItems = buildOrderItems(order, restaurant, request.getItems());
        order.setOrderItems(orderItems);

        Function<List<OrderItem>, Double> totalCalculator = items -> {
            double total = restaurant.getDeliveryFee();
            for (OrderItem item : items) {
                total += item.getItemPrice() * item.getQuantity();
            }
            return total;
        };

        order.setTotalAmount(totalCalculator.apply(orderItems));
        FoodOrder savedOrder = foodOrderRepository.save(order);

        enqueueOrder(savedOrder.getId());
        notificationService.sendOrderPlacedMessage(savedOrder);
        orderExecutorService.submit(() -> processQueuedOrder(savedOrder.getId()));

        return toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
        FoodOrder order = getOrderEntity(orderId);
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(request.getStatus().toUpperCase());
        } catch (Exception exception) {
            throw new BadRequestException("Invalid order status supplied");
        }

        order.setStatus(newStatus);
        FoodOrder savedOrder = foodOrderRepository.save(order);
        notificationService.sendStatusChangeMessage(savedOrder);
        return toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<OrderResponse> responses = new ArrayList<>();
        for (FoodOrder order : foodOrderRepository.findAll()) {
            responses.add(toOrderResponse(order));
        }
        return responses;
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        userService.getCustomerEntity(customerId);
        List<OrderResponse> responses = new ArrayList<>();
        for (FoodOrder order : foodOrderRepository.findByCustomerId(customerId)) {
            responses.add(toOrderResponse(order));
        }
        return responses;
    }

    private List<OrderItem> buildOrderItems(FoodOrder order, Restaurant restaurant, List<PlaceOrderItemRequest> requestItems) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (PlaceOrderItemRequest requestItem : requestItems) {
            if (requestItem.getQuantity() == null || requestItem.getQuantity() <= 0) {
                throw new BadRequestException("Quantity must be greater than zero");
            }

            MenuItem menuItem = restaurantService.getMenuItemEntity(requestItem.getMenuItemId());
            if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
                throw new BadRequestException("All menu items must belong to the selected restaurant");
            }
            if (!Boolean.TRUE.equals(menuItem.getAvailable())) {
                throw new BadRequestException("Menu item " + menuItem.getName() + " is not available");
            }

            orderItems.add(new OrderItem(order, menuItem, requestItem.getQuantity(), menuItem.getPrice()));
        }

        return orderItems;
    }

    private synchronized void enqueueOrder(Long orderId) {
        orderProcessingQueue.offer(orderId);
    }

    private void processQueuedOrder(Long orderId) {
        Long queuedOrderId;
        synchronized (this) {
            queuedOrderId = orderProcessingQueue.poll();
        }

        if (queuedOrderId == null || !queuedOrderId.equals(orderId)) {
            return;
        }

        FoodOrder order = getOrderEntity(orderId);
        order.setStatus(OrderStatus.CONFIRMED);
        foodOrderRepository.save(order);

        try {
            Thread.sleep(200);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }

        order.setStatus(OrderStatus.PREPARING);
        foodOrderRepository.save(order);
        notificationService.sendStatusChangeMessage(order);
    }

    private FoodOrder getOrderEntity(Long orderId) {
        return foodOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }

    private OrderResponse toOrderResponse(FoodOrder order) {
        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            itemResponses.add(OrderItemResponse.builder()
                    .menuItemId(item.getMenuItem().getId())
                    .menuItemName(item.getMenuItem().getName())
                    .quantity(item.getQuantity())
                    .itemPrice(item.getItemPrice())
                    .build());
        }

        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomer().getName())
                .restaurantName(order.getRestaurant().getName())
                .status(order.getStatus().name())
                .statusDescription(describeStatus(order.getStatus()))
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .notes(order.getNotes())
                .items(itemResponses)
                .build();
    }

    private String describeStatus(OrderStatus status) {
        switch (status) {
            case PLACED:
                return "Order received from customer";
            case CONFIRMED:
                return "Restaurant accepted the order";
            case PREPARING:
                return "Kitchen is preparing the order";
            case READY:
                return "Order is packed and ready";
            case OUT_FOR_DELIVERY:
                return "Delivery partner picked the order";
            case DELIVERED:
                return "Order delivered successfully";
            case CANCELLED:
                return "Order cancelled";
            default:
                return "Unknown status";
        }
    }
}
