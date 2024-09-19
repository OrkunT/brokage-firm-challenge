package com.brokerage.assetservice.domain.command.handler;

import com.brokerage.common.domain.command.UpdateAssetCommand;
import com.brokerage.common.domain.event.AssetUpdatedEvent;
import com.brokerage.common.domain.model.dto.Asset;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class AssetAggregate {

    @AggregateIdentifier
    private String assetId;
    private Long customerId;
    private String assetName;
    private Double size;
    private Double usableSize;

    public AssetAggregate() {
        // Required by Axon
    }

    @EventSourcingHandler
    public void on(AssetUpdatedEvent event) {
        Asset asset = event.getAsset();
        this.assetId = asset.getId();
        this.customerId = asset.getCustomerId();
        this.assetName = asset.getAssetName();
        this.size = asset.getSize();
        this.usableSize = asset.getUsableSize();
    }
}
