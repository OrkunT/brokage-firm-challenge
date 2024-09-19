package com.brokerage.common.domain.query;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ListOrdersQuery {
    private Long customerId = null;
    private final OrderState orderState;
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;

    public ListOrdersQuery(Long customerId, OrderState orderState, LocalDateTime startDate, LocalDateTime endDate) {
        this.customerId = customerId;
        this.orderState = orderState;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ListOrdersQuery(Long customerId, OrderState orderState) {
        this.customerId = customerId;
        this.orderState = orderState;
    }

    public ListOrdersQuery(OrderState orderState) {
        this.orderState = orderState;
    }

}

