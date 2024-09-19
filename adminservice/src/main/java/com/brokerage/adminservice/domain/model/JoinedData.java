package com.brokerage.adminservice.domain.model;

import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.common.domain.model.dto.Order;
import lombok.Data;

@Data
public class JoinedData {
    private Order order;
    private Asset asset;

    public JoinedData(Order order, Asset asset) {
        this.order = order;
        this.asset = asset;
    }

    @Override
    public String toString() {
        return "JoinedData{" +
                "order=" + order +
                ", asset=" + asset +
                '}';
    }
}
