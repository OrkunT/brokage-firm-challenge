package com.brokerage.assetservice.domain.command;

import com.brokerage.common.domain.model.dto.Asset;
import lombok.Getter;

@Getter
public class FindAssetsCommand {
    private final Long customerId;
    private final Asset asset;

    public FindAssetsCommand(Long customerId, Asset asset) {
        this.customerId = customerId;
        this.asset = asset;
    }

    // Getters
}
