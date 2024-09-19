package com.brokerage.assetservice;

import com.brokerage.common.domain.model.dto.Asset;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = {Asset.class, TokenEntry.class})
@SpringBootApplication
public class AssetserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetserviceApplication.class, args);
	}

}
