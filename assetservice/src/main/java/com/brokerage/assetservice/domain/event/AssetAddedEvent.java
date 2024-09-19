package com.brokerage.assetservice.domain.event;

import com.brokerage.common.domain.model.dto.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetAddedEvent {
    private final Asset asset;

    public AssetAddedEvent(Asset asset) {
        this.asset = asset;
    }

    // Getters
}

