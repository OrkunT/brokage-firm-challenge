package com.brokerage.adminservice.config;

import com.brokerage.adminservice.domain.command.handler.AssetAggregate;
import com.brokerage.adminservice.domain.command.handler.OrderAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public Repository<AssetAggregate> assetAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(AssetAggregate.class)
                .eventStore(eventStore)
                .build();
    }

    @Bean
    public Repository<OrderAggregate> orderAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(OrderAggregate.class)
                .eventStore(eventStore)
                .build();
    }
}
