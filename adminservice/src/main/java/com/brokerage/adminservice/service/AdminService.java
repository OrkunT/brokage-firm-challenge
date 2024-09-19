package com.brokerage.adminservice.service;

import com.brokerage.adminservice.domain.model.JoinedData;
import com.brokerage.common.domain.command.UpdateAssetCommand;
import com.brokerage.common.domain.command.UpdateOrderCommand;
import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.common.domain.model.dto.Order;
import com.brokerage.common.domain.query.ListOrdersQuery;
import com.brokerage.common.domain.query.OrderState;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    //Command gateway event handler implemented in order or asset service
    //improving scalability, separation of concerns by routing the events
    //to correct microservice via Axon build dispatcher
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public AdminService(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Transactional
    public void fetchMatchOrders(OrderState orderState) {
        ListOrdersQuery query = new ListOrdersQuery(orderState);
        long timeout = 5000; // Timeout in milliseconds

        Stream<Order> orderDataStream = queryGateway.scatterGather(
                query, ResponseTypes.instanceOf(Order.class), timeout, TimeUnit.MILLISECONDS
        );

        Stream<Asset> assetDataStream = queryGateway.scatterGather(
                query, ResponseTypes.instanceOf(Asset.class), timeout, TimeUnit.MILLISECONDS
        );

        // Step 1: Filter the order stream to get all pending orders
        List<Order> pendingOrders = orderDataStream
                .filter(order -> "PENDING".equals(order.getStatus()))
                .collect(Collectors.toList());

        // Step 2: Extract asset IDs from the filtered orders
        Set<String> assetIds = pendingOrders.stream()
                .map(Order::getAssetName)
                .collect(Collectors.toSet());

        // Step 3: Filter the asset stream based on the extracted asset IDs
        List<Asset> filteredAssets = assetDataStream
                .filter(asset -> assetIds.contains(asset.getAssetName()))
                .collect(Collectors.toList());

        // Step 4: Merge the filtered streams into a new data structure
        List<JoinedData> joinedDataList = pendingOrders.stream()
                .flatMap(order -> filteredAssets.stream()
                        .filter(asset -> asset.getAssetName().equals(order.getAssetName()))
                        .map(asset -> new JoinedData(order, asset)))
                .collect(Collectors.toList());



        joinedDataList.forEach(this::processOrderData);
    }

    //Reverts transactions using SAGA pattern
    //Commits first executes compensating action for rollback
    private void processOrderData(JoinedData order) {
        if ("BUY".equals(order.getOrder().getOrderSide())) {
            Asset tryAsset = order.getAsset();
            tryAsset.setCustomerId(order.getOrder().getCustomerId());
            tryAsset.setAssetName("TRY");
            tryAsset.setUsableSize(order.getOrder().getPrice() * order.getOrder().getSize());

            UpdateAssetCommand updateAssetCommand = new UpdateAssetCommand(tryAsset, "DECREASE");
            commandGateway.sendAndWait(updateAssetCommand);

            Asset asset = new Asset();
            asset.setCustomerId(order.getOrder().getCustomerId());
            asset.setAssetName(order.getOrder().getAssetName());
            asset.setSize(order.getOrder().getSize());
            asset.setUsableSize(order.getOrder().getSize());

            try {
                UpdateAssetCommand updateAssetCommand2 = new UpdateAssetCommand(asset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand2);
            } catch (Exception e) {
                updateAssetCommand = new UpdateAssetCommand(tryAsset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand);
            }

            try {
                UpdateOrderCommand updateOrderStatusCommand = new UpdateOrderCommand(order.getOrder(), "MATCHED");
                commandGateway.sendAndWait(updateOrderStatusCommand);
            } catch (Exception e) {
                //Reverse according to SAGA stage
                updateAssetCommand = new UpdateAssetCommand(tryAsset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand);
                UpdateAssetCommand updateAssetCommand2 = new UpdateAssetCommand(asset, "DECREASE");
                commandGateway.sendAndWait(updateAssetCommand2);
            }

        } else if ("SELL".equals(order.getOrder().getOrderSide())) {
            Asset asset = order.getAsset();
            asset.setCustomerId(order.getOrder().getCustomerId());
            asset.setAssetName(order.getOrder().getAssetName());
            asset.setUsableSize(order.getOrder().getSize());

            UpdateAssetCommand updateAssetCommand = new UpdateAssetCommand(asset, "DECREASE");
            commandGateway.sendAndWait(updateAssetCommand);

            Asset tryAsset = new Asset();
            tryAsset.setCustomerId(order.getOrder().getCustomerId());
            tryAsset.setAssetName("TRY");
            tryAsset.setSize(order.getOrder().getPrice() * order.getOrder().getSize());
            tryAsset.setUsableSize(order.getOrder().getPrice() * order.getOrder().getSize());
            try {
                UpdateAssetCommand updateAssetCommand2 = new UpdateAssetCommand(tryAsset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand2);
            } catch (Exception e) {
                updateAssetCommand = new UpdateAssetCommand(tryAsset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand);
            }

            try {


                UpdateOrderCommand updateOrderStatusCommand = new UpdateOrderCommand(order.getOrder(), "MATCHED");
                commandGateway.sendAndWait(updateOrderStatusCommand);
            } catch (Exception e) {
                //Reverse according to SAGA stage
                updateAssetCommand = new UpdateAssetCommand(tryAsset, "DECREASE");
                commandGateway.sendAndWait(updateAssetCommand);
                UpdateAssetCommand updateAssetCommand2 = new UpdateAssetCommand(asset, "INCREASE");
                commandGateway.sendAndWait(updateAssetCommand2);
            }
        }
    }

}
