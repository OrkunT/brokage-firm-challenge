package com.brokerage.adminservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;
    private String orderSide;
    private Double size;
    private Double price;
    private String status;
    private LocalDateTime createDate;

    // Default constructor
    public Order() {
    }

    // Constructor with parameters
    public Order(Long customerId, String assetName, String orderSide, Double size, Double price, String status, LocalDateTime createDate) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.size = size;
        this.price = price;
        this.status = status;
        this.createDate = createDate;
    }
}
