package org.example.spring.kafka.shared.kafka.error;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeadLetterService {

    public void writeDeadLetterEntry(final String topic, final String messageKey, final String rawMessage, final String failure) {
        // E.g. write to DLQ or external DB
    }
}
