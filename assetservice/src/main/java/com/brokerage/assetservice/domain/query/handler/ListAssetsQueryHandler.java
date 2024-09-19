package com.brokerage.assetservice.domain.query.handler;

import com.brokerage.assetservice.repository.AssetRepository;
import com.brokerage.common.domain.model.dto.Asset;
import com.brokerage.common.domain.query.ListAssetsQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAssetsQueryHandler {

    private final AssetRepository assetRepository;

    public ListAssetsQueryHandler(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> handle(ListAssetsQuery query) {
        Sort sort = query.getDirection().equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(query.getSortBy()).ascending()
                : Sort.by(query.getSortBy()).descending();
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);
        return assetRepository.findByCustomerId(query.getCustomerId(), pageable).getContent();
    }
}
