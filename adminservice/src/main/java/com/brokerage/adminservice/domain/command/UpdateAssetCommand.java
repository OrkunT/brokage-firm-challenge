package com.brokerage.adminservice.domain.command;

import com.brokerage.assetservice.domain.model.Asset;

public class UpdateAssetCommand {
    private final Asset asset;
    private final String operation; // "INCREASE" or "DECREASE"

    public UpdateAssetCommand(Asset asset, String operation) {
        this.asset = asset;
        this.operation = operation;
    }

    // Getters
    public Asset getAsset() {
        return asset;
    }

    public String getOperation() {
        return operation;
    }
}
