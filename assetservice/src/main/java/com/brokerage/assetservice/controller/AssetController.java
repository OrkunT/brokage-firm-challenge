package com.brokerage.assetservice.controller;

import com.brokerage.assetservice.model.Asset;
import com.brokerage.assetservice.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    public ResponseEntity<List<Asset>> listAssets(
            @RequestParam Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        List<Asset> assets = assetService.listAssets(customerId, page, size, sortBy, direction);
        System.out.println("Assets: " + assets); // Add this line for debugging
        return ResponseEntity.ok(assets);
    }
}
