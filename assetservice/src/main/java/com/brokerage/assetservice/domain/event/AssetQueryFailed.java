package com.brokerage.assetservice.domain.event;

public class AssetQueryFailed {
    private final String reason;

    public AssetQueryFailed(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
