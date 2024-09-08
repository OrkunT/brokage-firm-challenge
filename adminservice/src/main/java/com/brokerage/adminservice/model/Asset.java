package com.brokerage.adminservice.model;

import jakarta.persistence.*;
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
    public Asset(Long customerId, String assetName, Double size, Double usableSize) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;
    }
}