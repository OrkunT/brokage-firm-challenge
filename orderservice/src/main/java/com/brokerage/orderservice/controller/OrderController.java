package com.brokerage.orderservice.controller;
import com.brokerage.orderservice.domain.model.Order;
import com.brokerage.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Order>> createOrder(@Valid @RequestBody Order order) {
        System.out.println("Received Order: " + order);
        return orderService.createOrder(order)
                .thenApply(createdOrder -> {
                    System.out.println("Created Order: " + createdOrder);
                    return ResponseEntity.ok(createdOrder);
                })
                .exceptionally(ex -> {
                    System.err.println("Exception: " + ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Order>>> listOrders(@RequestParam Long customerId,
                                                                     @RequestParam LocalDateTime startDate,
                                                                     @RequestParam LocalDateTime endDate) {
        System.out.println("Listing Orders for Customer ID: " + customerId + ", Start Date: " + startDate + ", End Date: " + endDate);
        return orderService.listOrders(customerId, startDate, endDate)
                .thenApply(orders -> {
                    System.out.println("Fetched Orders: " + orders);
                    return ResponseEntity.ok(orders);
                });
    }

    @DeleteMapping("/{orderId}")
    public CompletableFuture<ResponseEntity<Object>> deleteOrder(@PathVariable String orderId) {
        System.out.println("Received request to delete Order ID: " + orderId);
        return orderService.findOrderById(orderId)
                .thenCompose(order -> {
                    if (order == null) {
                        System.out.println("Order not found: " + orderId);
                        return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
                    }

                    if ("PENDING".equals(order.getStatus())) {
                        System.out.println("Cancelling Order: " + orderId);
                        order.setStatus("CANCELED");
                        return orderService.updateOrder(order)
                                .thenApply(v -> ResponseEntity.noContent().build());
                    } else {
                        System.out.println("Order cannot be cancelled: " + orderId);
                        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Exception: " + ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}
