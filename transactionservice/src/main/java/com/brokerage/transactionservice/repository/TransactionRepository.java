package com.brokerage.transactionservice.repository;

import com.brokerage.common.domain.model.dto.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
