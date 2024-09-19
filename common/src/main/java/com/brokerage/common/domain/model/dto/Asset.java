package com.brokerage.common.domain.model.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Asset {

    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    private Long customerId;
    private String assetName;
    private Double size;
    private Double usableSize;

    // Constructor to generate UUID
    public Asset() {
        this.id = UUID.randomUUID().toString();
    }

    // Constructor with parameters
    public Asset(String assetName, Long customerId) {
        this.assetName = assetName;
        this.customerId = customerId;
    }

    public Asset(Long customerId, String assetName, Double size, Double usableSize) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;
    }

    public String getName() {
        return this.assetName;
    }

    // Getters and Setters
}

