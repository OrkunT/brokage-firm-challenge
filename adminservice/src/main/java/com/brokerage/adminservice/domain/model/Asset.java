package com.brokerage.adminservice.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;
    private Double size;
    private Double usableSize;

    // Default constructor
    public Asset() {
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

