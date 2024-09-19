package com.brokerage.transactionservice.domain.command;

import com.brokerage.common.domain.model.dto.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class removeAssetCommand {
    private final Long customerId;
    private final Asset asset;

    public removeAssetCommand(Long customerId, Asset asset) {
        this.customerId = customerId;
        this.asset = asset;
    }

    // Getters
}
