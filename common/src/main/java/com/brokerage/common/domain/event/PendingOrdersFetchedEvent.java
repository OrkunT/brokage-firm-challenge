package com.brokerage.common.domain.event;

import com.brokerage.common.domain.model.dto.Order;

import java.util.List;

public class PendingOrdersFetchedEvent {
    private final List<Order> pendingOrders;

    public PendingOrdersFetchedEvent(List<Order> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public List<Order> getPendingOrders() {
        return pendingOrders;
    }
}
