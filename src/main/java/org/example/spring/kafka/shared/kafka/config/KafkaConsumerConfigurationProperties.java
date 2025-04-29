package org.example.spring.kafka.shared.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "unused")
public class KafkaConsumerConfigurationProperties {
    private Listener listener = new Listener();
    private Handler handler = new Handler();


    @Data
    public static class Listener {
        /**
         * Name of consumed Kafka topic
         */
        private String topic;
        /**
         * Number of consumers for topic
         */
        private Integer concurrency = 1;
        /**
         * Name of consumer group
         */
        private String consumerGroup;
    }

    @Data
    public static class Handler {
        /**
         * Log each X Kafka message
         */
        private Integer logInterval = -1;
    }

}
