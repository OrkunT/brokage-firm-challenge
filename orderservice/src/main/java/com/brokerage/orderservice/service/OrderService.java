package com.brokerage.orderservice.service;
import com.brokerage.orderservice.model.Order;
import com.brokerage.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        order.setCreateDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }


    public void deleteOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent() && "PENDING".equals(order.get().getStatus())) {
            orderRepository.deleteById(orderId);
        } else {
            throw new IllegalStateException("Only pending orders can be deleted");
        }
    }

}
