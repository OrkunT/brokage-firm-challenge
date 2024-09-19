package com.brokerage.orderservice.domain.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final Long customerId;
    private final String assetName;
    private final Double orderSide;
    private final Double price;
    private final Double size;
    private final String status;
    private final LocalDateTime createDate;

    public UpdateOrderCommand(String orderId, Long customerId, String assetName, Double orderSide, Double price, Double size, String status, LocalDateTime createDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.price = price;
        this.size = size;
        this.status = status;
        this.createDate = createDate;
    }

    // Getters
}

