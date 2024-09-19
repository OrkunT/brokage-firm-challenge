package com.brokerage.orderservice.repository;

import com.brokerage.common.domain.model.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}*/

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndStatusAndCreateDateBetween(Long customerId,String status, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByCustomerIdAndStatus(Long customerId,String status);
    Optional<Order> findById(String orderId);
}