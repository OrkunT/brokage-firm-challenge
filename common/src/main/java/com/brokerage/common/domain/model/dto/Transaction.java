package com.brokerage.common.domain.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

//TODO: will use transaction later to implement CQRS pattern
@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;
    private Double amount;
    private LocalDateTime transactionDate;
    private String transactionType;

    public Transaction(Long customerId,Double amount,String assetName,String transactionType)
    {
        this.customerId = customerId;
        this.amount = amount;
        this.assetName = assetName;
        this.transactionType = transactionType;
    }
}
