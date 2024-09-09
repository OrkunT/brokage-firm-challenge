package com.brokerage.assetservice.domain.event;

import com.brokerage.assetservice.domain.model.Asset;
import lombok.Getter;

import java.util.List;

@Getter
public class AssetQueryCompleted {
    private List<Asset> assets;

    public AssetQueryCompleted(List<Asset> assets) {
        this.assets = assets;
    }

}
