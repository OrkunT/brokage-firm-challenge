package com.brokerage.common.domain.command;


import com.brokerage.common.domain.model.dto.Order;

public class UpdateOrderCommand {
    private final Order order;
    private final String status;

    public UpdateOrderCommand(Order order, String status) {
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
