package com.brokerage.assetservice.domain.saga;

import com.brokerage.assetservice.domain.event.AssetQueryCompleted;
import com.brokerage.assetservice.domain.event.AssetQueryFailed;
import com.brokerage.assetservice.domain.event.AssetQueryInitiated;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class AssetsSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void handle(AssetQueryInitiated event) {
        // Handle the asset query initiation event
        System.out.println("Asset query initiated for customer: " + event.getCustomerId());
        // Example: Trigger a validation command
        // commandGateway.send(new ValidateAssetQueryCommand(event.getCustomerId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "customerId")
    public void handle(AssetQueryCompleted event) {
        // Handle the asset query completion event
        System.out.println("Asset query completed with assets: " + event.getAssets());
        // Example: Trigger a follow-up command
        // commandGateway.send(new ProcessAssetsCommand(event.getAssets()));
    }

    @SagaEventHandler(associationProperty = "customerId")
    public void handle(AssetQueryFailed event) {
        // Handle the asset query failure event
        System.out.println("Asset query failed due to: " + event.getReason());
        // Example: Trigger a compensation command
        // commandGateway.send(new CompensateAssetQueryCommand(event.getReason()));
        SagaLifecycle.end();
    }
}
