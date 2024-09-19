package com.brokerage.assetservice.domain.command;

import com.brokerage.common.domain.model.dto.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updateAssetCommand {
    private final Long customerId;
    private final Asset asset;
    private String operation;

    public updateAssetCommand(Long customerId, Asset asset, String operation) {
        this.customerId = customerId;
        this.asset = asset;
        this.operation=operation;
    }

    // Getters
}
