package com.brokerage.adminservice.service;

import com.brokerage.adminservice.domain.command.FetchPendingOrdersCommand;
import com.brokerage.adminservice.domain.command.MatchOrdersCommand;
import com.brokerage.adminservice.domain.command.UpdateAssetCommand;
import com.brokerage.adminservice.domain.command.UpdateOrderStatusCommand;
import com.brokerage.adminservice.domain.event.PendingOrdersFetchedEvent;
import com.brokerage.assetservice.domain.model.Asset;
import com.brokerage.orderservice.domain.model.Order;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final CommandGateway commandGateway;

    public AdminService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Transactional
    public void matchOrders() {
        FetchPendingOrdersCommand fetchPendingOrdersCommand = new FetchPendingOrdersCommand();
        commandGateway.sendAndWait(fetchPendingOrdersCommand);
    }

    @EventHandler
    public void on(PendingOrdersFetchedEvent event) {
        List<Order> pendingOrders = event.getPendingOrders();

        MatchOrdersCommand matchOrdersCommand = new MatchOrdersCommand(pendingOrders);
        commandGateway.sendAndWait(matchOrdersCommand);

        for (Order order : pendingOrders) {
            if ("BUY".equals(order.getOrderSide())) {
                Asset tryAsset = new Asset();
                tryAsset.setCustomerId(order.getCustomerId());
                tryAsset.setAssetName("TRY");
                tryAsset.setUsableSize(order.getPrice() * order.getSize());

                UpdateAssetCommand updateAssetCommand = new UpdateAssetCommand(tryAsset, "DECREASE");
                commandGateway.sendAndWait(updateAssetCommand);

                Asset asset = new Asset();
                asset.setCustomerId(order.getCustomerId());
                asset.setAssetName(order.getAssetName());
                asset.setSize(order.getSize());
                asset.setUsableSize(order.getSize());

                UpdateAssetCommand updateAssetCommand2 = new UpdateAssetCommand(asset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand2);

                UpdateOrderStatusCommand updateOrderStatusCommand = new UpdateOrderStatusCommand(order, "MATCHED");
                commandGateway.sendAndWait(updateOrderStatusCommand);
            } else if ("SELL".equals(order.getOrderSide())) {
                Asset asset = new Asset();
                asset.setCustomerId(order.getCustomerId());
                asset.setAssetName(order.getAssetName());
                asset.setUsableSize(order.getSize());

                UpdateAssetCommand updateAssetCommand = new UpdateAssetCommand(asset, "DECREASE");
                commandGateway.sendAndWait(updateAssetCommand);

                Asset tryAsset = new Asset();
                tryAsset.setCustomerId(order.getCustomerId());
                tryAsset.setAssetName("TRY");
                tryAsset.setSize(order.getPrice() * order.getSize());
                tryAsset.setUsableSize(order.getPrice() * order.getSize());

                UpdateAssetCommand updateAssetCommand2 = new UpdateAssetCommand(tryAsset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand2);

                UpdateOrderStatusCommand updateOrderStatusCommand = new UpdateOrderStatusCommand(order, "MATCHED");
                commandGateway.sendAndWait(updateOrderStatusCommand);
            }
        }
    }
}
