package com.brokerage.adminservice.domain.event;


import com.brokerage.adminservice.domain.model.Order;
import lombok.Getter;

@Getter
public class OrderMatchedEvent {
    // Getters
    private final Order order;

    public OrderMatchedEvent(Order order) {
        this.order = order;
    }

}
