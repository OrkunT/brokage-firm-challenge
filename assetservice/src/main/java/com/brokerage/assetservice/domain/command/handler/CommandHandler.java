package com.brokerage.assetservice.domain.command.handler;

import com.brokerage.assetservice.domain.command.addAssetCommand;
import com.brokerage.assetservice.domain.command.updateAssetCommand;
import com.brokerage.assetservice.repository.AssetRepository;
import com.brokerage.common.domain.model.dto.Asset;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Component
public class CommandHandler {

    @Autowired
    private Repository<AssetAggregate> assetAggragateRepository;

    @Autowired
    AssetRepository assetRepository;

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @org.axonframework.commandhandling.CommandHandler
    public void handle(addAssetCommand command) throws Exception {

        assetRepository.save(command.getAsset());

        /*assetAggragateRepository.newInstance(() -> {
            AssetAggregate aggregate = new AssetAggregate(command);

            AggregateLifecycle.apply(new AssetFoundCreatedEvent(
                    command.getOrderId(),
                    command.getCustomerId(),
                    command.getAssetName(),
                    command.getOrderSide(),
                    command.getPrice(),
                    command.getSize()
            ));
            return aggregate;
        });*/
    }

    @org.axonframework.commandhandling.CommandHandler
    public void handle(updateAssetCommand command) {
        Asset asset = command.getAsset();
        String operation = command.getOperation();

        if ("DEPOSIT".equals(operation)) {
            asset.setSize(asset.getSize() + asset.getUsableSize());
            asset.setUsableSize(asset.getUsableSize() + asset.getUsableSize());
        } else if ("WITHDRAW".equals(operation)) {
            asset.setSize(asset.getSize() - asset.getUsableSize());
            asset.setUsableSize(0.0);
        }

        assetRepository.save(asset);

    }

}
