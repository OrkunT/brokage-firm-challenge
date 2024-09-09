package com.brokerage.adminservice.domain.command.handler;

import com.brokerage.adminservice.domain.command.MatchOrdersCommand;
import com.brokerage.adminservice.domain.command.UpdateOrderStatusCommand;
import com.brokerage.adminservice.domain.event.OrderMatchedEvent;
import com.brokerage.orderservice.domain.model.Order;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String status;

    public OrderAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public void handle(MatchOrdersCommand command) {
        // Logic to match orders
        // Apply OrderMatchedEvent for each matched order
    }

    @CommandHandler
    public void handle(UpdateOrderStatusCommand command) {
        Order order = command.getOrder();
        order.setStatus(command.getStatus());
        apply(new OrderMatchedEvent(order));
    }

    @EventSourcingHandler
    public void on(OrderMatchedEvent event) {
        Order order = event.getOrder();
        this.orderId = order.getId();
        this.status = order.getStatus();
    }
}
