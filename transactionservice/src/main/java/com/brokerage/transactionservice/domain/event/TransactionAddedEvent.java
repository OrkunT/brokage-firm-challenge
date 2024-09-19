package com.brokerage.transactionservice.domain.event;

import com.brokerage.common.domain.model.dto.Transaction;

import lombok.Data;
import lombok.Getter;

@Data
public class TransactionAddedEvent {
    private final Transaction transaction;

    public TransactionAddedEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    // Getters
}

