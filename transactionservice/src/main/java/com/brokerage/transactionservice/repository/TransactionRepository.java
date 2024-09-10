package com.brokerage.transactionservice.repository;

import com.brokerage.transactionservice.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
