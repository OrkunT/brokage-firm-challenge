package com.brokerage.adminservice.service;

import com.brokerage.adminservice.model.Asset;
import com.brokerage.adminservice.model.Order;
import com.brokerage.adminservice.repository.AssetRepository;
import com.brokerage.adminservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Transactional
    public void matchOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");

        for (Order order : pendingOrders) {
            if ("BUY".equals(order.getOrderSide())) {
                Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                        .orElseThrow(() -> new IllegalStateException("TRY asset not found for customer"));

                System.out.println("Initial TRY Size (BUY): " + tryAsset.getSize());

                if (tryAsset.getUsableSize() >= order.getPrice() * order.getSize()) {
                    double amountToDeduct = order.getPrice() * order.getSize();
                    tryAsset.setUsableSize(tryAsset.getUsableSize() - amountToDeduct);
                    tryAsset.setSize(tryAsset.getSize() - amountToDeduct); // Ensure both size and usableSize are updated
                    assetRepository.save(tryAsset);

                    System.out.println("Updated TRY Size (BUY): " + tryAsset.getSize());

                    Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                            .orElse(new Asset());
                    asset.setCustomerId(order.getCustomerId());
                    asset.setAssetName(order.getAssetName());
                    asset.setSize(asset.getSize() + order.getSize());
                    asset.setUsableSize(asset.getUsableSize() + order.getSize());
                    assetRepository.save(asset);

                    System.out.println("Updated " + order.getAssetName() + " Size (BUY): " + asset.getSize());

                    order.setStatus("MATCHED");
                    orderRepository.save(order);
                }
            } else if ("SELL".equals(order.getOrderSide())) {
                Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                        .orElseThrow(() -> new IllegalStateException("Asset not found for customer"));

                System.out.println("Initial " + order.getAssetName() + " Size (SELL): " + asset.getSize());

                if (asset.getUsableSize() >= order.getSize()) {
                    asset.setUsableSize(asset.getUsableSize() - order.getSize());
                    asset.setSize(asset.getSize() - order.getSize()); // Ensure both size and usableSize are updated
                    assetRepository.save(asset);

                    System.out.println("Updated " + order.getAssetName() + " Size (SELL): " + asset.getSize());

                    Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                            .orElse(new Asset());
                    double amountToAdd = order.getPrice() * order.getSize();
                    tryAsset.setCustomerId(order.getCustomerId());
                    tryAsset.setAssetName("TRY");
                    tryAsset.setSize(tryAsset.getSize() + amountToAdd);
                    tryAsset.setUsableSize(tryAsset.getUsableSize() + amountToAdd);
                    assetRepository.save(tryAsset);

                    System.out.println("Updated TRY Size (SELL): " + tryAsset.getSize());

                    order.setStatus("MATCHED");
                    orderRepository.save(order);
                }
            }
        }
    }
}
