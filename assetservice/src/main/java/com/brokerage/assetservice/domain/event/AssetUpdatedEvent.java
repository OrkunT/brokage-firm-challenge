package com.brokerage.assetservice.domain.event;

import com.brokerage.assetservice.domain.model.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetUpdatedEvent {
    private final Asset asset;

    public AssetUpdatedEvent(Asset asset) {
        this.asset = asset;
    }

    // Getters
}
