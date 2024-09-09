package com.brokerage.adminservice.domain.event;


import com.brokerage.orderservice.domain.model.Order;

public class OrderMatchedEvent {
    private final Order order;

    public OrderMatchedEvent(Order order) {
        this.order = order;
    }

    // Getters
    public Order getOrder() {
        return order;
    }
}
