package com.brokerage.assetservice.domain.event;

import com.brokerage.assetservice.domain.model.Asset;
import lombok.Getter;

@Getter
public class AssetFoundOrCreatedEvent {
    private final Asset asset;

    public AssetFoundOrCreatedEvent(Asset asset) {
        this.asset = asset;
    }

    // Getters
}
