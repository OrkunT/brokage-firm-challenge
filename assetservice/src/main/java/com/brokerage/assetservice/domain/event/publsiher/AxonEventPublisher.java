package com.brokerage.assetservice.domain.event.publsiher;

import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AxonEventPublisher implements EventPublisher {

    private final EventGateway eventGateway;

    @Autowired
    public AxonEventPublisher(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public void publish(Object event) {
        eventGateway.publish(event);
    }
}

