package com.brokerage.common.domain.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
public class Order {

    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    private Long customerId;

    private LocalDateTime createDate;

    private String assetName;

    private Double orderSide;

    private Double price;

    private Double size;

    private String status;

    // Constructor to generate UUID
    public Order() {
        this.id = UUID.randomUUID().toString();
    }

    public Order(Long customerId, LocalDateTime createDate, String assetName, Double orderSide, Double price, Double size, String status) {
        this.customerId = customerId;
        this.createDate = createDate;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.price = price;
        this.size = size;
        this.status = status;
        this.id = UUID.randomUUID().toString();
    }
    // Getters and Setters
}
