package com.brokerage.orderservice.domain.command.handler;

import com.brokerage.common.domain.model.dto.Order;
import com.brokerage.orderservice.domain.command.CreateOrderCommand;
import com.brokerage.orderservice.domain.command.UpdateOrderCommand;
import com.brokerage.orderservice.domain.event.OrderCreatedEvent;
import com.brokerage.orderservice.domain.event.OrderUpdatedEvent;
import com.brokerage.orderservice.repository.OrderRepository;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommandHandler {

    @Autowired
    private Repository<OrderAggregate> orderAggragateRepository;

    @Autowired
    OrderRepository orderRepository;

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @org.axonframework.commandhandling.CommandHandler
    public void handle(CreateOrderCommand command) throws Exception {
        Order order = new Order(command.getCustomerId(), LocalDateTime.now(), command.getAssetName(),
                command.getOrderSide(), command.getPrice(), command.getSize(), "PENDING");

        orderRepository.save(order);

        orderAggragateRepository.newInstance(() -> {
            OrderAggregate aggregate = new OrderAggregate(command);
            AggregateLifecycle.apply(new OrderCreatedEvent(
                    command.getOrderId(),
                    command.getCustomerId(),
                    command.getAssetName(),
                    command.getOrderSide(),
                    command.getPrice(),
                    command.getSize()
            ));
            return aggregate;
        });
    }

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @org.axonframework.commandhandling.CommandHandler
    public void handle(UpdateOrderCommand command) throws Exception {

        Order order = new Order(command.getCustomerId(), LocalDateTime.now(), command.getAssetName(),
                command.getOrderSide(), command.getPrice(), command.getSize(), "MATCHED");

        orderRepository.save(order);

        orderAggragateRepository.newInstance(() -> {
            OrderAggregate aggregate = new OrderAggregate(command);
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
            return aggregate;
        });
    }

}
