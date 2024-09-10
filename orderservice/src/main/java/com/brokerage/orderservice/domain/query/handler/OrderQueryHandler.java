package com.brokerage.orderservice.domain.query.handler;

import com.brokerage.orderservice.domain.model.Order;
import com.brokerage.orderservice.domain.query.FindOrderByIdQuery;
import com.brokerage.orderservice.domain.query.ListOrdersQuery;
import com.brokerage.orderservice.repository.OrderRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class OrderQueryHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryHandler.class);

    @Autowired
    private OrderRepository orderRepository;

    @QueryHandler
    public List<Order> handle(ListOrdersQuery query) {
        logger.info("Handling ListOrdersQuery: {}", query);
        List<Order> orders = orderRepository.findByCustomerIdAndCreateDateBetween(
                query.getCustomerId(), query.getStartDate(), query.getEndDate());
        logger.info("Found orders: {}", orders);
        return orders;
    }

    @QueryHandler
    public Order handle(FindOrderByIdQuery query) {
        logger.info("Handling FindOrderByIdQuery: {}", query);
        Order order = orderRepository.findById(query.getOrderId()).orElse(null);
        logger.info("Found order: {}", order);
        return order;
    }
}
