package com.brokerage.transactionservice.config;

import com.brokerage.transactionservice.domain.command.handler.TransactionAggregate;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.extensions.kafka.eventhandling.producer.DefaultProducerFactory;
import org.axonframework.extensions.kafka.eventhandling.producer.KafkaPublisher;
import org.axonframework.extensions.kafka.eventhandling.producer.ProducerFactory;
import org.axonframework.modelling.command.Repository;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class AxonConfig {

    @Bean
    public Configurer configurer(KafkaPublisher<String, byte[]> kafkaPublisher, @Qualifier("xStreamSerializer") Serializer serializer) {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();
        configurer.eventProcessing(eventProcessingConfigurer ->
                eventProcessingConfigurer.registerEventHandler(configuration -> kafkaPublisher)
        );
        configurer.configureSerializer(c -> serializer);
        configurer.configureAggregate(TransactionAggregate.class);
        return configurer;
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }

    @Bean
    public EventBus eventBus() {
        return SimpleEventBus.builder().build();
    }

    @Bean
    public KafkaPublisher<String, byte[]> kafkaPublisher(@Qualifier("axonProducerFactory") ProducerFactory<String, byte[]> producerFactory) {
        return KafkaPublisher.<String, byte[]>builder()
                .producerFactory(producerFactory)
                .topicResolver(event -> Optional.of("your-topic-name")) // Example topic resolver
                .build();
    }

    @Bean(name = "axonProducerFactory")
    public ProducerFactory<String, byte[]> axonProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return DefaultProducerFactory.<String, byte[]>builder()
                .configuration(configProps)
                .build();
    }

    @Bean
    public EventStore eventStore() {
        return EmbeddedEventStore.builder()
                .storageEngine(new InMemoryEventStorageEngine())
                .build();
    }

    @Bean
    public Repository<TransactionAggregate> orderAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(TransactionAggregate.class)
                .eventStore(eventStore)
                .build();
    }
}
