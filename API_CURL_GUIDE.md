# API Curl Guide

Base URL used below:

```text
http://localhost:8080
```

## 1. UserController

### a. `registerCustomer`

```bash
curl --request POST 'http://localhost:8080/users/customers' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Amit Sharma",
  "email": "amit@gmail.com",
  "phone": "9999999999",
  "deliveryAddress": "Delhi"
}'
```

### b. `registerAdmin`

```bash
curl --request POST 'http://localhost:8080/users/admins' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Neha Singh",
  "email": "neha@gmail.com",
  "phone": "8888888888",
  "supportLevel": "L1"
}'
```

### c. `getAllCustomers`

```bash
curl --request GET 'http://localhost:8080/users/customers'
```

### d. `getAllAdmins`

```bash
curl --request GET 'http://localhost:8080/users/admins'
```

## 2. RestaurantController

### a. `createRestaurant`

```bash
curl --request POST 'http://localhost:8080/restaurants' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Spice Route",
  "category": "Indian",
  "deliveryFee": 40.0
}'
```

### b. `addMenuItem`

```bash
curl --request POST 'http://localhost:8080/restaurants/1/menu-items' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Paneer Wrap",
  "description": "Spicy paneer wrap",
  "price": 120.0,
  "available": true
}'
```

### c. `getAllRestaurants`

```bash
curl --request GET 'http://localhost:8080/restaurants'
```

### d. `getMenuByRestaurant`

```bash
curl --request GET 'http://localhost:8080/restaurants/1/menu-items'
```

### e. `searchMenuItems`

```bash
curl --request GET 'http://localhost:8080/restaurants/menu-items/search?keyword=wrap'
```

### f. `searchMenuItems` with `restaurantId`

```bash
curl --request GET 'http://localhost:8080/restaurants/menu-items/search?keyword=wrap&restaurantId=1'
```

## 3. OrderController

### a. `placeOrder`

```bash
curl --request POST 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
  "customerId": 1,
  "restaurantId": 1,
  "notes": "Less spicy",
  "items": [
    {
      "menuItemId": 1,
      "quantity": 2
    },
    {
      "menuItemId": 2,
      "quantity": 1
    }
  ]
}'
```

### b. `updateOrderStatus`

```bash
curl --request PATCH 'http://localhost:8080/orders/1/status' \
--header 'Content-Type: application/json' \
--data-raw '{
  "status": "READY"
}'
```

### c. `getAllOrders`

```bash
curl --request GET 'http://localhost:8080/orders'
```

### d. `getOrdersByCustomer`

```bash
curl --request GET 'http://localhost:8080/orders/customer/1'
```

## 4. ReportController

### a. `generateSnapshot`

```bash
curl --request GET 'http://localhost:8080/reports/snapshot'
```

## Suggested Execution Order

Run APIs in this order for demo:

1. `registerCustomer`
2. `registerAdmin`
3. `createRestaurant`
4. `addMenuItem`
5. `addMenuItem` again for one more dish
6. `getAllRestaurants`
7. `getMenuByRestaurant`
8. `placeOrder`
9. `getAllOrders`
10. `updateOrderStatus`
11. `getOrdersByCustomer`
12. `generateSnapshot`
