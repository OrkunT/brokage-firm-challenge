package com.brokerage.assetservice.service;

import com.brokerage.assetservice.domain.command.addAssetCommand;
import com.brokerage.assetservice.domain.command.removeAssetCommand;
import com.brokerage.assetservice.domain.model.Asset;
import com.brokerage.assetservice.domain.query.ListAssetsQuery;
import com.brokerage.assetservice.domain.query.handler.ListAssetsQueryHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addAsset(Long customerId, Asset asset) {
        addAssetCommand command = new addAssetCommand(customerId, asset);
        commandGateway.send(command);
    }

    public void removeAsset(Long customerId, Asset asset) {
        removeAssetCommand command = new removeAssetCommand(customerId, asset);
        commandGateway.send(command);
    }
}