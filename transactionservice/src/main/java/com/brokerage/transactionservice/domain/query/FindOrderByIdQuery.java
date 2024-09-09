package com.brokerage.transactionservice.domain.query;

import lombok.Getter;

@Getter
public class FindOrderByIdQuery {
    private final String orderId;

    public FindOrderByIdQuery(String orderId) {
        this.orderId = orderId;
    }

}
