package com.brokerage.adminservice.domain.event;

import com.brokerage.adminservice.domain.model.Asset;

public class AssetUpdatedEvent {
    private final Asset asset;

    public AssetUpdatedEvent(Asset asset) {
        this.asset = asset;
    }

    // Getters
    public Asset getAsset() {
        return asset;
    }
}
