package com.brokerage.common;

import com.brokerage.common.domain.model.dto.Asset;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class CommonApplication {

	public static void main(String[] args) {
           SpringApplication.run(CommonApplication.class,args);
	}
}
