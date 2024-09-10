package com.brokerage.adminservice.domain.event;

import com.brokerage.adminservice.domain.model.Order;

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
