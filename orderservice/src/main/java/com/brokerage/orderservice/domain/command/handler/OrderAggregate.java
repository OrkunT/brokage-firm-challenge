package com.brokerage.orderservice.domain.command.handler;

import com.brokerage.orderservice.domain.command.CreateOrderCommand;
import com.brokerage.orderservice.domain.command.UpdateOrderCommand;
import com.brokerage.orderservice.domain.event.OrderCreatedEvent;
import com.brokerage.orderservice.domain.event.OrderUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateLifecycle;

import java.time.LocalDateTime;
import java.util.UUID;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private UUID orderId;
    private Long customerId;
    private String assetName;
    private String orderSide;
    private Double price;
    private Double size;
    private String status;
    private LocalDateTime createDate;

    // Default constructor required by Axon
    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getOrderId(),
                command.getCustomerId(),
                command.getAssetName(),
                command.getOrderSide(),
                command.getPrice(),
                command.getSize()
        ));
    }

    @CommandHandler
    public void handle(UpdateOrderCommand command) {
        // Apply necessary events if needed
        AggregateLifecycle.apply(new OrderUpdatedEvent(
                command.getOrderId(),
                command.getCustomerId(),
                command.getAssetName(),
                command.getOrderSide(),
                command.getPrice(),
                command.getSize(),
                command.getStatus(),
                command.getCreateDate()
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = UUID.fromString(event.getOrderId());
        this.customerId = event.getCustomerId();
        this.assetName = event.getAssetName();
        this.orderSide = event.getOrderSide();
        this.price = event.getPrice();
        this.size = event.getSize();
        this.status = "PENDING";
        this.createDate = LocalDateTime.now();
    }

    @EventSourcingHandler
    public void on(OrderUpdatedEvent event) {
        this.customerId = event.getCustomerId();
        this.assetName = event.getAssetName();
        this.orderSide = event.getOrderSide();
        this.price = event.getPrice();
        this.size = event.getSize();
        this.status = event.getStatus();
        this.createDate = event.getCreateDate();
    }
}
