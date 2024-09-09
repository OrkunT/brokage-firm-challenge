package com.brokerage.transactionservice.domain.command.handler;

import com.brokerage.transactionservice.domain.command.AddTransactionCommand;
import com.brokerage.transactionservice.domain.event.TransactionAddedEvent;
import com.brokerage.transactionservice.model.Transaction;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class TransactionAggregate {

    @AggregateIdentifier
    private Long transactionId;
    private Long customerId;
    private Double amount;
    private String type;
    private String iban;

    public TransactionAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public TransactionAggregate(AddTransactionCommand command) {
        Transaction transaction = new Transaction(command.getCustomerId(), command.getAmount(), command.getType(), command.getIban());
        apply(new TransactionAddedEvent(transaction));
    }

    @EventSourcingHandler
    public void on(TransactionAddedEvent event) {
        Transaction transaction = event.getTransaction();
        this.transactionId = transaction.getId();
        this.customerId = transaction.getCustomerId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.iban = transaction.getIban();
    }
}
