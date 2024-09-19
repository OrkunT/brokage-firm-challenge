package com.brokerage.assetservice.service;

import com.brokerage.assetservice.domain.command.addAssetCommand;
import com.brokerage.assetservice.domain.command.updateAssetCommand;
import com.brokerage.assetservice.domain.query.handler.ListAssetsQueryHandler;
import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.common.domain.query.ListAssetsQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AssetService {
    private final ListAssetsQueryHandler listAssetsQueryHandler;
    private final CommandGateway commandGateway;

    public AssetService(ListAssetsQueryHandler listAssetsQueryHandler, CommandGateway commandGateway) {
        this.listAssetsQueryHandler = listAssetsQueryHandler;
        this.commandGateway = commandGateway;
    }

    public List<Asset> listAssets(Long customerId, int page, int size, String sortBy, String direction) {
        ListAssetsQuery query = new ListAssetsQuery(customerId, page, size, sortBy, direction);
        return listAssetsQueryHandler.handle(query);
    }

    public CompletableFuture<Asset> addAsset(Asset asset) {
        Long customerId = asset.getCustomerId();
        addAssetCommand command = new addAssetCommand(customerId, asset);
        commandGateway.send(command);
        return commandGateway.send(command).thenApply(result -> {
            System.out.println("Service - Created Order: " + asset);
            return asset;
        });
    }

    public CompletableFuture<ResponseEntity<Object>>  removeAsset(Long customerId, Asset asset) {
        updateAssetCommand command = new updateAssetCommand(customerId,asset,"SELL");
        return commandGateway.send(command).thenApply(result -> {
            return null;
        });
    }
}