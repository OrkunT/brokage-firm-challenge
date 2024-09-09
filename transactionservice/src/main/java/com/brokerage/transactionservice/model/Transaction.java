package com.brokerage.transactionservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Double amount;
    private String type; // DEPOSIT or WITHDRAW
    private String iban; // Only for withdrawals
    private LocalDateTime transactionDate;

    public Transaction(){};

    public Transaction(Long customerId, Double amount, String type, String iban) {
        this.customerId = customerId;
        this.amount = amount;
        this.type = type;
        this.iban = iban;
        this.transactionDate = LocalDateTime.now(); // Set the transaction date to the current date and time
    }

    // Getters and Setters
}
