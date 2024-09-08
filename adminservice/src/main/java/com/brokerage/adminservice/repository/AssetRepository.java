package com.brokerage.adminservice.repository;

import com.brokerage.adminservice.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);

    Optional<Asset> findByAssetNameAndCustomerId(String assetName, Long customerId);
}
