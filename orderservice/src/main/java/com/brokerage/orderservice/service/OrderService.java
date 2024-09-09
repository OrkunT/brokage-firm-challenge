package com.brokerage.orderservice.service;

import com.brokerage.orderservice.domain.command.CreateOrderCommand;
import com.brokerage.orderservice.domain.command.DeleteOrderCommand;
import com.brokerage.orderservice.domain.command.UpdateOrderCommand;
import com.brokerage.orderservice.domain.model.Order;
import com.brokerage.orderservice.domain.query.FindOrderByIdQuery;
import com.brokerage.orderservice.domain.query.ListOrdersQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Service
public class OrderService {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    public CompletableFuture<Order> createOrder(Order order) {
        UUID orderId = UUID.randomUUID();
        order.setId(orderId.toString());
        order.setStatus("PENDING");
        order.setCreateDate(LocalDateTime.now());

        System.out.println("Service - Creating Order: " + order);

        CreateOrderCommand command = new CreateOrderCommand(order.getId().toString(), order.getCustomerId(), order.getAssetName(), order.getOrderSide(), order.getPrice(), order.getSize());
        return commandGateway.send(command).thenApply(result -> {
            System.out.println("Service - Created Order: " + order);
            return order;
        });
    }

    public CompletableFuture<List<Order>> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("Service - Listing Orders for Customer ID: " + customerId + ", Start Date: " + startDate + ", End Date: " + endDate);
        ListOrdersQuery query = new ListOrdersQuery(customerId, startDate, endDate);
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(Order.class)).thenApply(orders -> {
            System.out.println("Service - Fetched Orders: " + orders);
            return orders;
        });
    }

    public CompletableFuture<Order> findOrderById(String orderId) {
        System.out.println("Service - Finding Order by ID: " + orderId);
        FindOrderByIdQuery query = new FindOrderByIdQuery(orderId);
        return queryGateway.query(query, ResponseTypes.instanceOf(Order.class)).thenApply(order -> {
            System.out.println("Service - Found Order: " + order);
            return order;
        });
    }

    public CompletableFuture<Void> updateOrder(Order order) {
        System.out.println("Service - Updating Order: " + order);
        UpdateOrderCommand command = new UpdateOrderCommand(
                order.getId(), order.getCustomerId(), order.getAssetName(), order.getOrderSide(),
                order.getPrice(), order.getSize(), order.getStatus(), order.getCreateDate());
        return commandGateway.send(command).thenApply(result -> {
            System.out.println("Service - Updated Order: " + order);
            return null;
        });
    }

    public CompletableFuture<Void> deleteOrder(String orderId) {
        System.out.println("Service - Deleting Order: " + orderId);
        return findOrderById(orderId).thenCompose(order -> {
            if (order != null && "PENDING".equals(order.getStatus())) {
                DeleteOrderCommand command = new DeleteOrderCommand(orderId.toString());
                return commandGateway.send(command).thenApply(result -> {
                    System.out.println("Service - Deleted Order: " + orderId);
                    return null;
                });
            } else {
                throw new IllegalStateException("Only pending orders can be deleted");
            }
        });
    }
}

