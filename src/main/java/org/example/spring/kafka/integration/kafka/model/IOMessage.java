package org.example.spring.kafka.integration.kafka.model;

import lombok.Builder;

@Builder // für test
public record IOMessage(
    // Business Object
    String value
) {
}
