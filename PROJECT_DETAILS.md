# Online Food Order Management System

## 1. Project Goal

Build a Spring Boot based REST API for an online food ordering platform where:

- customers can register
- admins can register
- restaurants can be created
- menu items can be added
- customers can place orders
- the system updates order status
- reports can be generated
- order processing is handled asynchronously

The project is intentionally designed for classroom use and covers only the concepts already taught:

- OOP advanced: `this`, `super`, inheritance, polymorphism, abstract class, interface
- Lambda expressions and functional interfaces: `Predicate`, `Consumer`, `Function`
- Collections: `List`, `Set`, `Map`, `Queue`
- Multithreading: `Thread`, thread pool, `join()`
- Maven
- Spring Framework core concepts: DI, IoC, bean configuration
- Spring Boot
- Spring MVC and REST APIs
- Spring AOP, Lombok, logging
- Hibernate / JPA with SQL database

## 2. Features To Build

### User Module

- Register customer
- Register admin
- View all customers
- View all admins

### Restaurant Module

- Create restaurant
- View all restaurants
- Add menu item to a restaurant
- View restaurant menu
- Search menu items by keyword
- Search menu items by keyword within one restaurant

### Order Module

- Place an order for a restaurant
- Multiple food items in one order
- Store quantity and price for each order item
- Auto-calculate final bill including delivery fee
- View all orders
- View orders by customer
- Update order status manually

### Asynchronous Processing Module

- When an order is placed, it is pushed into a `Queue`
- A thread-pool task picks it up
- Status moves automatically from `PLACED` to `CONFIRMED` to `PREPARING`

### Reporting Module

- Generate business snapshot report
- Count orders by status
- Calculate revenue by restaurant
- Show total customers and total restaurants
- Use two threads and `join()` to build the report

### Logging Module

- Log controller and service execution time
- Log failures through AOP

## 3. Functional Requirements

1. The system shall allow customer registration.
2. The system shall allow admin registration.
3. The system shall allow restaurant creation.
4. The system shall allow adding multiple menu items under a restaurant.
5. The system shall allow searching menu items by name or description.
6. The system shall allow placing an order only for one restaurant at a time.
7. The system shall reject unavailable menu items.
8. The system shall calculate total order amount from:
   `sum(itemPrice * quantity) + deliveryFee`
9. The system shall save all order details in SQL using Hibernate.
10. The system shall process orders in background threads.
11. The system shall expose REST APIs for all major operations.
12. The system shall generate report data from persisted orders.

## 4. Non-Functional Requirements

- Clean layered architecture
- Controller, service, repository separation
- Simple exception handling with `@ResponseStatus`
- Readable package structure
- Uses only syllabus-aligned concepts
- Should run directly through Maven

## 5. LLD

### 5.1 Package Structure

```text
com.myimdb.searchbook
├── aspect
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
└── service
    └── impl
```

### 5.2 Core Classes

#### `BaseUser`

- Type: `abstract class`
- Responsibility:
  - store common user fields
  - act as parent class for all user types
- Fields:
  - `id`
  - `name`
  - `email`
  - `phone`
- Method:
  - `describeUser()`

#### `Customer extends BaseUser`

- Additional fields:
  - `deliveryAddress`
  - `loyaltyPoints`
- Demonstrates:
  - inheritance
  - `super(...)`
  - runtime polymorphism by overriding `describeUser()`

#### `AdminUser extends BaseUser`

- Additional field:
  - `supportLevel`
- Demonstrates:
  - inheritance
  - method overriding

#### `Restaurant`

- Fields:
  - `id`
  - `name`
  - `category`
  - `deliveryFee`
  - `open`

#### `MenuItem`

- Fields:
  - `id`
  - `name`
  - `description`
  - `price`
  - `available`
  - `restaurant`

#### `FoodOrder`

- Fields:
  - `id`
  - `customer`
  - `restaurant`
  - `status`
  - `orderDate`
  - `totalAmount`
  - `notes`
  - `orderItems`

#### `OrderItem`

- Fields:
  - `id`
  - `foodOrder`
  - `menuItem`
  - `quantity`
  - `itemPrice`

### 5.3 Relationships

- One `Restaurant` has many `MenuItem`
- One `Customer` can have many `FoodOrder`
- One `FoodOrder` has many `OrderItem`
- One `OrderItem` refers to one `MenuItem`

### 5.4 Interfaces

#### `UserService`
- customer/admin operations

#### `RestaurantService`
- restaurant/menu operations

#### `OrderService`
- order lifecycle operations

#### `ReportService`
- report generation

#### `NotificationService`
- send order and status messages

### 5.5 Service Implementations

#### `UserServiceImpl`

- extends `AbstractUserRegistrationService`
- validates common user fields
- uses `Consumer<Customer>` for loyalty point initialization

#### `RestaurantServiceImpl`

- uses:
  - `Predicate<MenuItem>` for search filtering
  - `Function<MenuItem, MenuItemResponse>` for mapping
  - `Consumer<List<RestaurantResponse>>` for sorting
- contains overloaded `searchMenuItems(...)`
  - compile-time polymorphism example

#### `OrderServiceImpl`

- validates customer, restaurant, menu item ownership
- stores order in DB
- uses `Queue<Long>` for processing order ids
- uses `ExecutorService` thread pool for async processing
- uses `Function<List<OrderItem>, Double>` for bill calculation

#### `ReportServiceImpl`

- creates two threads:
  - status summary thread
  - revenue summary thread
- waits using `join()`

### 5.6 AOP Design

#### `ApplicationLoggerAspect`

- around advice for:
  - controllers
  - service implementation methods
- logs:
  - method name
  - execution time
  - exception details

### 5.7 Collections Used

- `List`
  - restaurant menu items
  - order items
  - API responses
- `Set`
  - unique restaurant categories collected in service
- `Map`
  - report summaries for status count and restaurant revenue
- `Queue`
  - background order processing queue

### 5.8 Multithreading Design

#### Thread Pool

- Bean created in configuration class:
  - `Executors.newFixedThreadPool(3)`
- Used for background order processing

#### `join()`

- Used in reporting
- Ensures both worker threads finish before final response is returned

## 6. REST API List

### User APIs

#### `POST /users/customers`

```json
{
  "name": "Amit",
  "email": "amit@gmail.com",
  "phone": "9999999999",
  "deliveryAddress": "Delhi"
}
```

#### `POST /users/admins`

```json
{
  "name": "Neha",
  "email": "neha@gmail.com",
  "phone": "8888888888",
  "supportLevel": "L1"
}
```

#### `GET /users/customers`

#### `GET /users/admins`

### Restaurant APIs

#### `POST /restaurants`

```json
{
  "name": "Spice Route",
  "category": "Indian",
  "deliveryFee": 40.0
}
```

#### `POST /restaurants/{restaurantId}/menu-items`

```json
{
  "name": "Paneer Wrap",
  "description": "Spicy paneer wrap",
  "price": 120.0,
  "available": true
}
```

#### `GET /restaurants`

#### `GET /restaurants/{restaurantId}/menu-items`

#### `GET /restaurants/menu-items/search?keyword=wrap`

#### `GET /restaurants/menu-items/search?keyword=wrap&restaurantId=1`

### Order APIs

#### `POST /orders`

```json
{
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
}
```

#### `PATCH /orders/{orderId}/status`

```json
{
  "status": "READY"
}
```

#### `GET /orders`

#### `GET /orders/customer/{customerId}`

### Report API

#### `GET /reports/snapshot`

## 7. Database Tables

### `base_user`

- single-table inheritance data for customer/admin
- key columns:
  - `id`
  - `user_type`
  - `name`
  - `email`
  - `phone`
  - `delivery_address`
  - `loyalty_points`
  - `support_level`

### `restaurant`

- `id`
- `name`
- `category`
- `delivery_fee`
- `open`

### `menu_item`

- `id`
- `name`
- `description`
- `price`
- `available`
- `restaurant_id`

### `food_orders`

- `id`
- `customer_id`
- `restaurant_id`
- `status`
- `order_date`
- `total_amount`
- `notes`

### `order_item`

- `id`
- `order_id`
- `menu_item_id`
- `quantity`
- `item_price`

## 8. How To Build This In Class

1. Create Spring Boot project with:
   - Spring Web
   - Spring Data JPA
   - Spring AOP
   - Lombok
   - H2 Database
2. Configure SQL and Hibernate in `application.properties`
3. Create entities and mappings
4. Create repository interfaces
5. Create service interfaces
6. Implement service classes
7. Create controllers
8. Add simple exception classes
9. Add AOP logging
10. Add thread pool configuration
11. Implement async order processing and report generation
12. Test in Postman

## 9. Concepts Mapping

### OOP Advanced

- `this`
  - constructors and field assignment
- `super`
  - child entities calling parent constructor
- inheritance
  - `Customer` and `AdminUser` inherit `BaseUser`
- late binding
  - overridden `describeUser()`
- early binding
  - overloaded `searchMenuItems(...)`
- abstract class
  - `BaseUser`, `AbstractUserRegistrationService`
- interface
  - all service contracts

### Java 8 Lambdas and Functional Interfaces

- `Predicate`
  - search filter
- `Consumer`
  - initialize loyalty points, process report counting, sorting
- `Function`
  - menu mapping and total bill calculation

### Collections

- `List`, `Set`, `Map`, `Queue`

### Multithreading

- `ExecutorService`
  - async order processing
- `Thread`
  - report workers
- `join()`
  - thread synchronization for report generation

## 10. Run Instructions

```bash
./mvnw spring-boot:run
```

H2 console:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:food_order_db`
- username: `sa`
- password: empty

## 11. Suggested 6-8 Hour Breakdown

### Hour 1
- project setup and dependencies

### Hour 2
- entities and DB mapping

### Hour 3
- repositories and services

### Hour 4
- controllers and request/response DTOs

### Hour 5
- order logic and total calculation

### Hour 6
- multithreading and queue processing

### Hour 7
- AOP logging and simple exception handling

### Hour 8
- testing and cleanup
