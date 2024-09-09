package com.brokerage.orderservice.domain.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
@Setter
public class DeleteOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    public DeleteOrderCommand(String orderId) {
        this.orderId = orderId;
    }

}

