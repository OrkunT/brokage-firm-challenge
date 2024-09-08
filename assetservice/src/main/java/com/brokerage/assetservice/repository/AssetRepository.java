package com.brokerage.assetservice.repository;

import com.brokerage.assetservice.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Page<Asset> findByCustomerId(Long customerId, Pageable pageable);
}