package com.brokerage.transactionservice.domain.event;

import com.brokerage.transactionservice.domain.model.Transaction;
import lombok.Getter;

@Getter
public class TransactionAddedEvent {
    private final Transaction transaction;

    public TransactionAddedEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    // Getters
}

