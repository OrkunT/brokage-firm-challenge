package com.brokerage.transactionservice.controller;

import com.brokerage.common.domain.model.dto.Transaction;
import com.brokerage.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> depositMoney(@RequestParam Long customerId, @RequestParam Double amount) {
        Transaction transaction = transactionService.depositMoney(customerId, amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdrawMoney(@RequestParam Long customerId, @RequestParam Double amount, @RequestParam String iban) {
        Transaction transaction = transactionService.withdrawMoney(customerId, amount, iban);
        return ResponseEntity.ok(transaction);
    }
}
