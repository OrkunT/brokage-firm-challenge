package com.brokerage.transactionservice.service;

import com.brokerage.transactionservice.model.Asset;
import com.brokerage.transactionservice.model.Transaction;
import com.brokerage.transactionservice.repository.AssetRepository;
import com.brokerage.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AssetRepository assetRepository;

    //Since TRY is another type of asset we can treat it as one
    @Transactional
    public Transaction depositMoney(Long customerId, Double amount) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                .orElseGet(() -> {
                    Asset newAsset = new Asset();
                    newAsset.setCustomerId(customerId);
                    newAsset.setAssetName("TRY");
                    newAsset.setSize(0.0);
                    newAsset.setUsableSize(0.0);

                    //TODO: Add other service duplicate transaction repo so unless write both complete, transaction fails
                    return assetRepository.save(newAsset);
                });

        tryAsset.setSize(tryAsset.getSize() + amount);
        tryAsset.setUsableSize(tryAsset.getUsableSize() + amount);
        assetRepository.save(tryAsset);

        Transaction transaction = new Transaction();
        transaction.setCustomerId(customerId);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());

        //TODO: Add other service duplicate transaction repo so unless write both complete, transaction fails
        return transactionRepository.save(transaction);
    }


    @Transactional
    public Transaction withdrawMoney(Long customerId, Double amount, String iban) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                .orElseThrow(() -> new IllegalStateException("TRY asset not found for customer"));

        if (tryAsset.getUsableSize() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        tryAsset.setSize(tryAsset.getSize() - amount);
        tryAsset.setUsableSize(tryAsset.getUsableSize() - amount);

        //TODO: Add other service duplicate transaction repo so unless write both complete, transaction fails
        assetRepository.save(tryAsset);

        Transaction transaction = new Transaction();
        transaction.setCustomerId(customerId);
        transaction.setAmount(amount);
        transaction.setType("WITHDRAW");
        transaction.setIban(iban);
        transaction.setTransactionDate(LocalDateTime.now());

        //TODO: Add other service duplicate transaction repo so before write both transaction fails
        return transactionRepository.save(transaction);
    }
}
