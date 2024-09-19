package com.brokerage.transactionservice;

import com.brokerage.common.domain.model.dto.Order;
import com.brokerage.common.domain.model.dto.Transaction;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = {Order.class, Transaction.class, TokenEntry.class})
@SpringBootApplication
public class TransactionserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionserviceApplication.class, args);
	}

}
