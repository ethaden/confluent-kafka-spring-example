package org.example.spring.kafka.shared.kafka.config;

import org.example.spring.kafka.shared.kafka.error.DeadLetterService;
import org.example.spring.kafka.shared.kafka.error.KafkaErrorHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.CommonErrorHandler;

@Configuration
@EnableKafka
@EnableConfigurationProperties({KafkaConsumerConfigurationProperties.class})
public class KafkaConsumerConfiguration {

    @Bean
    public CommonErrorHandler errorHandler(final DeadLetterService deadLetterService) {
        return new KafkaErrorHandler(deadLetterService);
    }
}