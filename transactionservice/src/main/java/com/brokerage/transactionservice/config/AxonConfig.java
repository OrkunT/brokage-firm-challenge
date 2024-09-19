package com.brokerage.transactionservice.config;

import com.brokerage.transactionservice.domain.command.handler.TransactionAggregate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class AxonConfig {
        @Bean
        public CommandGateway commandGatewayWithRetry(CommandBus commandBus) {

            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
            IntervalRetryScheduler rs = IntervalRetryScheduler.builder().retryExecutor(scheduledExecutorService).maxRetryCount(5).retryInterval(1000).build();
            return DefaultCommandGateway.builder().commandBus(commandBus).retryScheduler(rs).build();
        }

        @Bean(destroyMethod = "")
        public DeadlineManager deadlineManager(TransactionManager transactionManager,
                                               org.axonframework.config.Configuration config) {
            return SimpleDeadlineManager.builder()
                    .transactionManager(transactionManager)
                    .scopeAwareProvider(new ConfigurationScopeAwareProvider(config))
                    .build();
        }

        @Bean(destroyMethod = "shutdown")
        public ScheduledExecutorService workerExecutorService() {
            return Executors.newScheduledThreadPool(4);
        }

        @Autowired
        public void configureSerializers(ObjectMapper objectMapper) {
            objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }

        @Bean
        public ConfigurerModule eventProcessingCustomizer() {
            return configurer -> configurer
                    .eventProcessing()
                    .registerPooledStreamingEventProcessor(
                            "com.brokerage.transactionservice.domain.command.handler",
                            org.axonframework.config.Configuration::eventStore,
                            (c, b) -> b.workerExecutor(workerExecutorService())
                                    .batchSize(100)
                    );
        }
    }
