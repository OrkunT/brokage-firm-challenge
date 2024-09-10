package com.brokerage.transactionservice.domain.event;

import java.time.LocalDateTime;

public class OrderCompletedEvent {
    private final String orderId;
    private final Long customerId;
    private final String assetName;
    private final String orderSide;
    private final Double price;
    private final Double size;
    private final String completionStatus;
    private final LocalDateTime completionDate;

    // Constructor
    public OrderCompletedEvent(String orderId, Long customerId, String assetName, String orderSide, Double price, Double size, String completionStatus, LocalDateTime completionDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.price = price;
        this.size = size;
        this.completionStatus = completionStatus;
        this.completionDate = completionDate;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getOrderSide() {
        return orderSide;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSize() {
        return size;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
}

