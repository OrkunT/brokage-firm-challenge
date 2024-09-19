package com.brokerage.transactionservice.domain.command;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddTransactionCommand {
    private final Long customerId;
    private final Double amount;
    private final String type; // "deposit" or "withdraw"
    private final String name;

    public AddTransactionCommand(Long customerId, Double amount,  String transactionName,String transactionType) {
        this.customerId = customerId;
        this.amount = amount;
        this.type = transactionType;
        this.name = transactionName;
    }

    // Getters
}
