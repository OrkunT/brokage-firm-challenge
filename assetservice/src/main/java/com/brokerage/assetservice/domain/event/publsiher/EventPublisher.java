package com.brokerage.assetservice.domain.event.publsiher;

public interface EventPublisher {
    void publish(Object event);
}
