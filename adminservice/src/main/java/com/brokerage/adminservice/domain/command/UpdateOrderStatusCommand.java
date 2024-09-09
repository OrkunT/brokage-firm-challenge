package com.brokerage.adminservice.domain.command;


import com.brokerage.orderservice.domain.model.Order;

public class UpdateOrderStatusCommand {
    private final Order order;
    private final String status;

    public UpdateOrderStatusCommand(Order order, String status) {
        this.order = order;
        this.status = status;
    }

    // Getters
    public Order getOrder() {
        return order;
    }

    public String getStatus() {
        return status;
    }
}
