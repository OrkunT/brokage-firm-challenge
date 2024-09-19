package com.brokerage.assetservice.domain.event;

public class AssetQueryInitiated {
    private final Long customerId;

    public AssetQueryInitiated(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
