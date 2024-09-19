package com.brokerage.orderservice.controller;

import com.brokerage.common.domain.model.dto.Order;
import com.brokerage.common.domain.query.OrderState;
import com.brokerage.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Order>> createOrder(@Valid @RequestBody Order order) {
        logger.info("Received Order: {}", order);
        return orderService.createOrder(order)
                .thenApply(createdOrder -> {
                    logger.info("Created Order: {}", createdOrder);
                    return ResponseEntity.ok(createdOrder);
                })
                .exceptionally(ex -> {
                    logger.error("Exception: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Order>>> listOrders(@RequestParam Long customerId,
                                                                     @RequestParam OrderState orderStatus,
                                                                     @RequestParam LocalDateTime startDate,
                                                                     @RequestParam LocalDateTime endDate) {
        logger.info("Listing Orders for Customer ID: {}, Start Date: {}, End Date: {}", customerId, startDate, endDate);
        return orderService.listOrders(customerId, orderStatus, startDate, endDate)
                .thenApply(orders -> {
                    logger.info("Fetched Orders: {}", orders);
                    return ResponseEntity.ok(orders);
                });
    }

    @DeleteMapping("/{orderId}")
    public CompletableFuture<ResponseEntity<Object>> deleteOrder(@PathVariable String orderId) {
        logger.info("Received request to delete Order ID: {}", orderId);
        return orderService.findOrderById(orderId)
                .thenCompose(order -> {
                    if (order == null) {
                        logger.info("Order not found: {}", orderId);
                        return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
                    }

                    if ("PENDING".equals(order.getStatus())) {
                        logger.info("Cancelling Order: {}", orderId);
                        order.setStatus("CANCELED");
                        return orderService.updateOrder(order)
                                .thenApply(v -> ResponseEntity.noContent().build());
                    } else {
                        logger.info("Order cannot be cancelled: {}", orderId);
                        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
                    }
                })
                .exceptionally(ex -> {
                    logger.error("Exception: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}