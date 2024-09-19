package com.brokerage.adminservice;

import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.common.domain.model.dto.Order;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = {Asset.class, Order.class, TokenEntry.class})
@SpringBootApplication
public class AdminserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminserviceApplication.class, args);
	}
}
