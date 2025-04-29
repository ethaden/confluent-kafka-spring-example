package org.example.spring.kafka.integration.kafka.consumer;

import org.example.spring.kafka.integration.io.customEventData.CustomEventDataService;
import org.example.spring.kafka.integration.kafka.handler.IOPayloadHandler;
import org.example.spring.kafka.integration.kafka.handler.LoggingIOPayloadHandler;
import org.example.spring.kafka.integration.kafka.model.IOMessage;
import org.example.spring.kafka.shared.kafka.config.KafkaConsumerConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "custom-properties-prefix.listener.topic")
class CustomEventDataIOConsumerConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "custom-properties-prefix")
    KafkaConsumerConfigurationProperties customEventDataIoProperties() {
        return new KafkaConsumerConfigurationProperties();
    }

    @Bean
    LoggingIOPayloadHandler customEventDataIoPayloadHandler(final KafkaConsumerConfigurationProperties customEventDataIoProperties, final CustomEventDataService customEventDataService) {
        return new LoggingIOPayloadHandler(customEventDataIoProperties.getHandler().getLogInterval()) {

            @Override
            protected void handleInternal(final String key, final IOMessage payload) {
                customEventDataService.processEvent(key, payload);
            }
        };
    }

    @Bean
    IOTopicConsumer customEventDataIoConsumer(final KafkaConsumerConfigurationProperties customEventDataIoProperties, final IOPayloadHandler customEventDataIoPayloadHandler) {
        return new IOTopicConsumer(customEventDataIoProperties, customEventDataIoPayloadHandler);
    }

}
