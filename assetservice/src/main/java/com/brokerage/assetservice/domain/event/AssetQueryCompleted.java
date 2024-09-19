package com.brokerage.assetservice.domain.event;

import com.brokerage.common.domain.model.dto.Asset;
import lombok.Getter;

import java.util.List;

@Getter
public class AssetQueryCompleted {
    private final List<Asset> assets;

    public AssetQueryCompleted(List<Asset> assets) {
        this.assets = assets;
    }

}
