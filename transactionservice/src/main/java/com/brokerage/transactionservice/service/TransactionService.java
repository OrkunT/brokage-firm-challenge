package com.brokerage.transactionservice.service;

import com.brokerage.transactionservice.domain.command.FindOrCreateAssetCommand;
import com.brokerage.transactionservice.domain.command.removeAssetCommand;
import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.transactionservice.domain.command.AddTransactionCommand;
import com.brokerage.common.domain.model.dto.Transaction;
import com.brokerage.transactionservice.repository.TransactionRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final CommandGateway commandGateway;
    private final TransactionRepository transactionRepository;

    public TransactionService(CommandGateway commandGateway, TransactionRepository transactionRepository) {
        this.commandGateway = commandGateway;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction depositMoney(Long customerId, Double amount) {
        FindOrCreateAssetCommand findOrCreateAssetCommand = new FindOrCreateAssetCommand(customerId, "TRY");
        Asset tryAsset = commandGateway.sendAndWait(findOrCreateAssetCommand);

        tryAsset.setUsableSize(amount); // Set the amount to be updated

        removeAssetCommand updateAssetCommand = new removeAssetCommand(tryAsset.getCustomerId(),tryAsset);
        commandGateway.sendAndWait(updateAssetCommand);

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(customerId, amount, "TRY","DEPOSIT");
        commandGateway.sendAndWait(addTransactionCommand);

        Transaction transaction = new Transaction(customerId, amount, "TRY","DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdrawMoney(Long customerId, Double amount, String iban) {
        FindOrCreateAssetCommand findOrCreateAssetCommand = new FindOrCreateAssetCommand(customerId, "TRY");
        Asset tryAsset = commandGateway.sendAndWait(findOrCreateAssetCommand);

        if (tryAsset.getUsableSize() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        tryAsset.setUsableSize(amount); // Set the amount to be updated

        try {
                removeAssetCommand updateAssetCommand = new removeAssetCommand(tryAsset.getCustomerId(), tryAsset);
                commandGateway.sendAndWait(updateAssetCommand);
        }
        catch (Exception e)
        {
            return null;
        }
        Transaction transaction = new Transaction(customerId, amount, "TRY","WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
}
