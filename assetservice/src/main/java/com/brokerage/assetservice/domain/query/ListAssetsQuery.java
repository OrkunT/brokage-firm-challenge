package com.brokerage.assetservice.domain.query;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ListAssetsQuery {
    private Long customerId;
    private int page;
    private int size;
    private String sortBy;
    private String direction;

    public ListAssetsQuery(Long customerId, int page, int size, String sortBy, String direction) {
        this.customerId = customerId;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.direction = direction;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}



