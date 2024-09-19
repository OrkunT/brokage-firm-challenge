package com.brokerage.assetservice.domain.command;

import com.brokerage.common.domain.model.dto.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addAssetCommand {
    private final Long customerId;
    private final Asset asset;

    public addAssetCommand(Long customerId, Asset asset) {
        this.customerId = customerId;
        this.asset = asset;
    }

    // Getters
}