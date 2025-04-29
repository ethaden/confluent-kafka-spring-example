package org.example.spring.kafka.integration.kafka.handler;

import java.util.Map;

import org.example.spring.kafka.integration.kafka.model.IOMessage;

public interface IOPayloadHandler {

    void handle(final Map<String, Object> kafkaHeaders, final String key, final IOMessage payload);

}
