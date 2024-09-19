package com.brokerage.common.domain.command;

import com.brokerage.common.domain.model.dto.Asset;

public class UpdateAssetCommand {
    private final Asset asset;
    private final String operation;

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
