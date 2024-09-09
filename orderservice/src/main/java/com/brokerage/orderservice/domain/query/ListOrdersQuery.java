package com.brokerage.orderservice.domain.query;

import java.time.LocalDateTime;

public class ListOrdersQuery {
    private final Long customerId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public ListOrdersQuery(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}

