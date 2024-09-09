package com.brokerage.adminservice;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Order {

	// Getters and setters (if needed)
	@NotNull(message = "Customer ID is mandatory")
	private Long customerId;

	@NotNull(message = "Create date is mandatory")
	private LocalDateTime createDate;

	@NotNull(message = "Asset name is mandatory")
	private String assetName;

	@NotNull(message = "Order side is mandatory")
	private String orderSide;

	@NotNull(message = "Price is mandatory")
	@Positive(message = "Price must be positive")
	private Double price;

	@NotNull(message = "Size is mandatory")
	@Positive(message = "Size must be positive")
	private Double size;

	@NotNull(message = "Status is mandatory")
	private String status;

	private String id;

	// Default constructor to generate UUID
	public Order() {
		this.id = UUID.randomUUID().toString();
	}

	// Constructor with parameters
	public Order(Long customerId, LocalDateTime createDate, String assetName, String orderSide, Double price, Double size, String status) {
		this.customerId = customerId;
		this.createDate = createDate;
		this.assetName = assetName;
		this.orderSide = orderSide;
		this.price = price;
		this.size = size;
		this.status = status;
		this.id = UUID.randomUUID().toString();
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public void setOrderSide(String orderSide) {
		this.orderSide = orderSide;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(String id) {
		this.id = id;
	}
}
