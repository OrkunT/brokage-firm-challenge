package com.brokerage.orderservice.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
//TODO: will use transaction later to implement CQRS pattern
@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;
    private Double amount;
    private LocalDateTime transactionDate;

    // Getters and Setters
}
