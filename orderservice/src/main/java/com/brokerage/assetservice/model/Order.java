package com.brokerage.assetservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // Getters and Setters
}
