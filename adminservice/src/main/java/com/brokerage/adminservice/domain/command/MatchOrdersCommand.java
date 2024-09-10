package com.brokerage.adminservice.domain.command;


import com.brokerage.adminservice.domain.model.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class MatchOrdersCommand {
    private final List<Order> pendingOrders;

    public MatchOrdersCommand(List<Order> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

}
