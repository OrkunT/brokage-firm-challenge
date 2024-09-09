package com.brokerage.transactionservice.domain.saga;

import com.brokerage.orderservice.domain.event.OrderCompletedEvent;
import com.brokerage.orderservice.domain.event.OrderCreatedEvent;
import com.brokerage.orderservice.domain.event.OrderUpdatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        // Handle the order creation event
        // Trigger validation service
        System.out.println("Order created: " + event.getOrderId());
        // Example: Trigger a validation command
        // commandGateway.send(new ValidateOrderCommand(event.getOrderId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCompletedEvent event) {
        // Handle the order validation event
        System.out.println("Order validated: " + event.getOrderId());
        // Example: Trigger a payment command
        // commandGateway.send(new ProcessPaymentCommand(event.getOrderId()));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent event) {
        // Handle the order cancellation event
        System.out.println("Order cancelled: " + event.getOrderId());
        // Example: Trigger a refund command
        // commandGateway.send(new ProcessRefundCommand(event.getOrderId()));
        SagaLifecycle.end();
    }

}

