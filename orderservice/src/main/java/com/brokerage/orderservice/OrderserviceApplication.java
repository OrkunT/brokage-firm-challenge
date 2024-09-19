package com.brokerage.orderservice;

import com.brokerage.common.domain.model.dto.Order;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan(basePackageClasses = {Order.class, TokenEntry.class})
@SpringBootApplication
public class OrderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}
}
