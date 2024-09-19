package com.brokerage.assetservice.controller;

import com.brokerage.assetservice.service.AssetService;
import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.common.domain.model.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    private static final Logger logger = LoggerFactory.getLogger(AssetController.class);

    @GetMapping
    public ResponseEntity<List<Asset>> listAssets(
            @RequestParam Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        List<Asset> assets = assetService.listAssets(customerId, page, size, sortBy, direction);
        return ResponseEntity.ok(assets);
    }


    @PostMapping
    public CompletableFuture<ResponseEntity<Asset>> addAsset(@RequestBody Asset asset) {
        logger.info("Received add Asset: {}", asset);
        return assetService.addAsset(asset)
                .thenApply(addedAsset -> {
                    logger.info("Created Asset: {}", addedAsset);
                    return ResponseEntity.ok(addedAsset);
                })
                .exceptionally(ex -> {
                    logger.error("Exception: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @DeleteMapping("/{orderId}")
    public CompletableFuture<ResponseEntity<Object>> removeAsset(@PathVariable Long customerId,Asset asset) {
        logger.info("Received request to remove Customer ID: {}, Asset ID: {}", customerId,asset.getId());
        return assetService.removeAsset(customerId,asset)
                .thenCompose(order -> {
                    if (order == null) {
                        logger.info("Asset not found: {}", asset.getId());
                        return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
                    }
                    return null;
                })
                .exceptionally(ex -> {
                    logger.error("Exception: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}
