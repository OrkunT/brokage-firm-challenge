package com.brokerage.adminservice.domain.command.handler;

import com.brokerage.adminservice.domain.command.UpdateAssetCommand;
import com.brokerage.adminservice.domain.event.AssetUpdatedEvent;
import com.brokerage.adminservice.domain.model.Asset;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class AssetAggregate {

    @AggregateIdentifier
    private Long assetId;
    private Long customerId;
    private String assetName;
    private Double size;
    private Double usableSize;

    public AssetAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public void handle(UpdateAssetCommand command) {
        Asset asset = command.getAsset();
        String operation = command.getOperation();

        if ("INCREASE".equals(operation)) {
            asset.setSize(asset.getSize() + asset.getUsableSize());
            asset.setUsableSize(asset.getUsableSize() + asset.getUsableSize());
        } else if ("DECREASE".equals(operation)) {
            asset.setSize(asset.getSize() - asset.getUsableSize());
            asset.setUsableSize(asset.getUsableSize() - asset.getUsableSize());
        }

        apply(new AssetUpdatedEvent(asset));
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
