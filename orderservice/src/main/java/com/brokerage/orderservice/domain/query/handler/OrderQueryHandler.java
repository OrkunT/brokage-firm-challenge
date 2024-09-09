package com.brokerage.orderservice.domain.query.handler;

import com.brokerage.orderservice.domain.model.Order;
import com.brokerage.orderservice.domain.query.FindOrderByIdQuery;
import com.brokerage.orderservice.domain.query.ListOrdersQuery;
import com.brokerage.orderservice.repository.OrderRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderQueryHandler {

    @Autowired
    private OrderRepository orderRepository;

    @QueryHandler
    public List<Order> handle(ListOrdersQuery query) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(
                query.getCustomerId(), query.getStartDate(), query.getEndDate());
    }

    @QueryHandler
    public Order handle(FindOrderByIdQuery query) {
        return orderRepository.findById(query.getOrderId()).orElse(null);
    }
}

