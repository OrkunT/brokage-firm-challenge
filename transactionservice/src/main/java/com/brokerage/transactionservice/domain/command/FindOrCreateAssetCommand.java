package com.brokerage.transactionservice.domain.command;

import lombok.Getter;

@Getter
public class FindOrCreateAssetCommand {
    private final Long customerId;
    private final String assetName;

    public FindOrCreateAssetCommand(Long customerId, String assetName) {
        this.customerId = customerId;
        this.assetName = assetName;
    }

    // Getters
}
