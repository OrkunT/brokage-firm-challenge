package com.brokerage.common.domain.event;

import com.brokerage.common.domain.model.dto.Asset;

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
