package com.brokerage.orderservice.domain.command;

import com.brokerage.orderservice.repository.OrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final Long customerId;
    private final String assetName;
    private final Double orderSide;
    private final Double price;
    private final Double size;

    private OrderRepository orderRepository;

    public CreateOrderCommand(String orderId, Long customerId, String assetName, Double orderSide, Double price, Double size) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.price = price;
        this.size = size;
    }
}