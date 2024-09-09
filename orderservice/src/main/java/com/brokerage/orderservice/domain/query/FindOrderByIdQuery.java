package com.brokerage.orderservice.domain.query;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FindOrderByIdQuery {
    private final String orderId;

    public FindOrderByIdQuery(String orderId) {
        this.orderId = orderId;
    }

}
