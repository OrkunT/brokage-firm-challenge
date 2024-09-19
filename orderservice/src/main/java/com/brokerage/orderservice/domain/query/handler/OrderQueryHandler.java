package com.brokerage.orderservice.domain.query.handler;

import com.brokerage.common.domain.query.ListOrdersQuery;
import com.brokerage.common.domain.model.dto.Order;
import com.brokerage.common.domain.query.FindOrderByIdQuery;
import com.brokerage.orderservice.repository.OrderRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderQueryHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryHandler.class);

    @Autowired
    public OrderRepository orderRepository;

    @QueryHandler
    public List<Order> handle(ListOrdersQuery query) {
        logger.info("Handling ListOrdersQuery: {}", query);
        List<Order> orders = null;
        if(query.getCustomerId()==null)
        {
            orders = orderRepository.findAll();
        } else if (query.getEndDate()==null || query.getStartDate()==null) {
            orders = orderRepository.findByCustomerIdAndStatus(query.getCustomerId(),query.getOrderState().toString());
        } else{
            orders = orderRepository.findByCustomerIdAndStatusAndCreateDateBetween(query.getCustomerId(),query.getOrderState().toString(),query.getStartDate(),query.getEndDate());
        }
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
