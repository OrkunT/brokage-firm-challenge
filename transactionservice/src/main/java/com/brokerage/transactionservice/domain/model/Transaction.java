package com.brokerage.transactionservice.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String type;
    private String iban;

    public Transaction(){};

    public Transaction(Long customerId, String assetName, Double amount, LocalDateTime transactionDate) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction(Long customerId, Double amount, String type, String iban) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.amount = amount;
        this.type = type;
        this.iban = iban;
    }

    // Getters and Setters
}
