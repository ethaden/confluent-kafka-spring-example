package org.example.spring.kafka.integration.kafka.consumer;

import org.example.spring.kafka.integration.kafka.handler.IOPayloadHandler;
import org.example.spring.kafka.integration.kafka.model.IOMessage;
import org.example.spring.kafka.shared.kafka.config.KafkaConsumerConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@RequiredArgsConstructor
public class IOTopicConsumer {

    @Getter
    private final KafkaConsumerConfigurationProperties kafkaConsumerConfigurationProperties;
    private final IOPayloadHandler ioPayloadHandler;

    @KafkaListener(
        id = "#{__listener.kafkaConsumerConfigurationProperties.listener.consumerGroup}",
        clientIdPrefix = "client.#{__listener.kafkaConsumerConfigurationProperties.listener.topic}.consumer",
        topics = "#{__listener.kafkaConsumerConfigurationProperties.listener.topic}",
        concurrency = "#{__listener.kafkaConsumerConfigurationProperties.listener.concurrency}"
    )
    public void consume(final @Headers Map<String, Object> kafkaHeaders,
                        final @Header(name = KafkaHeaders.RECEIVED_KEY) String messageKey,
                        final @Payload(required = false) IOMessage payload,
                        final Acknowledgment ack) {

        ioPayloadHandler.handle(kafkaHeaders, messageKey, payload);
        ack.acknowledge();
    }
}
