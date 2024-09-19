package com.brokerage.common.domain.command.handler;

import com.brokerage.common.domain.command.UpdateOrderCommand;
import com.brokerage.common.domain.event.OrderMatchedEvent;
import com.brokerage.common.domain.model.dto.Order;
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
    public void handle(UpdateOrderCommand command) {
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
