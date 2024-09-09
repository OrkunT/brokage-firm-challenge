package com.brokerage.assetservice.domain.command.handler;

import com.brokerage.assetservice.domain.command.addAssetCommand;
import com.brokerage.assetservice.domain.command.removeAssetCommand;
import com.brokerage.assetservice.domain.command.FindOrCreateAssetCommand;
import com.brokerage.assetservice.domain.event.AssetAddedEvent;
import com.brokerage.assetservice.domain.event.AssetFoundOrCreatedEvent;
import com.brokerage.assetservice.domain.event.AssetUpdatedEvent;
import com.brokerage.assetservice.domain.model.Asset;
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
    private String name;
    private String type;

    public AssetAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public AssetAggregate(addAssetCommand command) {
        Asset asset = command.getAsset();
        apply(new AssetAddedEvent(asset));
    }


    @CommandHandler
    public void handle(FindOrCreateAssetCommand command) {
        // Logic to find or create the asset
        Asset asset = new Asset();
        asset.setCustomerId(command.getCustomerId());
        asset.setAssetName(command.getAssetName());
        asset.setSize(0.0);
        asset.setUsableSize(0.0);
        apply(new AssetFoundOrCreatedEvent(asset));
    }

    @CommandHandler
    public void handle(removeAssetCommand command) {
        Asset asset = command.getAsset();

            asset.setSize(asset.getSize() - asset.getUsableSize());
            asset.setUsableSize(asset.getUsableSize() - asset.getUsableSize());

        apply(new AssetUpdatedEvent(asset));
    }

    @CommandHandler
    public void handle(addAssetCommand command) {
        Asset asset = command.getAsset();

            asset.setSize(asset.getSize() + asset.getUsableSize());
            asset.setUsableSize(asset.getUsableSize() + asset.getUsableSize());


        apply(new AssetUpdatedEvent(asset));
    }


    @EventSourcingHandler
    public void on(AssetFoundOrCreatedEvent event) {
        Asset asset = event.getAsset();
        this.assetId = asset.getId();
        this.customerId = asset.getCustomerId();
    }


    @EventSourcingHandler
    public void on(AssetAddedEvent event) {
        Asset asset = event.getAsset();
        this.assetId = asset.getId();
        this.customerId = asset.getCustomerId();
        this.name = asset.getName();
        this.type = asset.getName();
    }

    @EventSourcingHandler
    public void on(AssetUpdatedEvent event) {
        Asset asset = event.getAsset();
        this.name = asset.getName();
        this.type = asset.getName();
    }
}
