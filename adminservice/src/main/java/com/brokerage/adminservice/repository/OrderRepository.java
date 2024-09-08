package com.brokerage.adminservice.repository;

import com.brokerage.adminservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);

    List<Order> findByAssetNameAndCustomerId(String assetName, Long customerId);
}
