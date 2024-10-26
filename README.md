# Micropods Project

This project demonstrates a simple microservices architecture with four main services: Inventory Service, Order Service, Product Service, and a Discovery Server. Key resiliency patterns such as circuit breakers are used with `Resilience4J`.

## Table of Contents

1. [Services](#services)
2. [Tech Stack](#tech-stack)
3. [Circuit Breaker Setup](#circuit-breaker-setup)
4. [Configuration](#configuration)

---

## Services

### 1. Inventory Service

Manages the stock levels for each product. It responds to requests from the Order Service to verify the availability of items.

### 2. Order Service

Handles order creation and coordinates with the Inventory Service to confirm the stock availability for items in each order. If items are available, it finalizes the order; otherwise, it returns an error.

### 3. Product Service

Provides a catalog of products. It handles product information, which can be queried directly by clients or other services.

### 4. Discovery Server

Acts as a service registry for the microservices, allowing each service to dynamically register itself and discover other services. This is based on Netflix Eureka.

---

## Tech Stack

- **Java / Spring Boot**: Core framework for microservices.
- **Resilience4J**: Circuit breaker and resilience patterns.
- **Netflix Eureka**: Service discovery.
- **Spring Cloud Gateway**: API gateway for routing.
- **MySQL/ MongoDb**: Database for storing product and inventory data.

---

## Circuit Breaker Setup

Resilience4J is used for implementing circuit breaker patterns to enhance service resilience, especially in scenarios where dependencies may be unreliable.

### Case 1: Inventory Service Failure

When the Inventory Service is unavailable, the Order Service will trigger the circuit breaker:

- **Condition**: After a specified number of failed network calls to the Inventory Service.
- **Response**: Circuit breaker transitions to an `OPEN` state and halts further requests to the Inventory Service.
- **Recovery**: After a configured interval, it automatically retries to check if the service is back up and reverts to a `CLOSED` state.

Configuration (in `application.yml` for Order Service):

```yaml
resilience4j.circuitbreaker:
  instances:
    inventoryService:
      register-health-indicator: true
      sliding-window-size: 5
      minimum-number-of-calls: 3
      failure-rate-threshold: 50
      wait-duration-in-open-state: 10s
```

### Case 2: Slow Network Connections / Database Queries

In cases where calls to the Inventory Service are slower than expected (e.g., network latency or slow database queries):

- **Timeout**: A 3-second timeout is configured to ensure that requests taking longer are declined, allowing the system to fail fast.
- **Fallback**: In case of timeout, Order Service can return a fallback response to inform users of the delay.

Configuration:

```yaml
resilience4j.timelimiter:
  instances:
    inventoryService:
      timeout-duration: 3s
```

### Prerequisites

- JDK 17
- Maven

### Running Services

Each service can be started independently.

1. **Build and run services**

   ```bash
   mvn clean install
   mvn spring-boot:run -pl inventory-service
   mvn spring-boot:run -pl order-service
   mvn spring-boot:run -pl product-service
   mvn spring-boot:run -pl discovery-server
   ```

---

## Configuration

- **Discovery Server**: Update Eureka configurations in each service’s `application.yml`.
- **Database**: Configure data source (MySQL/MongoDb) settings in `application.yml` for Inventory and Product Services.
- **Circuit Breaker**: Customize Resilience4J settings in `application.yml`.

---
