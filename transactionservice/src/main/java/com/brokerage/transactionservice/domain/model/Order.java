package com.brokerage.transactionservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

    @NotNull(message = "create date is mandatory")
    private LocalDateTime createDate;

    @NotNull(message = "Asset name is mandatory")
    private String assetName;

    @NotNull(message = "Order side is mandatory")
    private String orderSide;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Size is mandatory")
    @Positive(message = "Size must be positive")
    private Double size;

    @NotNull(message = "Status is mandatory")
    private String status;

    // Constructor to generate UUID
    public Order() {
        this.id = UUID.randomUUID().toString();
    }

    public Order(Long customerId, LocalDateTime createDate, String assetName, String orderSide, Double price, Double size, String status) {
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
