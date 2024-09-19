package com.brokerage.common.domain.event;


import com.brokerage.common.domain.model.dto.Order;
import lombok.Getter;

@Getter
public class OrderMatchedEvent {
    // Getters
    private final Order order;

    public OrderMatchedEvent(Order order) {
        this.order = order;
    }

}
