package org.example.spring.kafka.integration.kafka.handler;

import org.example.spring.kafka.integration.kafka.model.IOMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LoggingIOPayloadHandler implements IOPayloadHandler {

    private static final ThreadLocal<Long> messageCount = ThreadLocal.withInitial(() -> 0L);

    private final Integer logInterval;

    @Override
    public void handle(final Map<String, Object> kafkaHeaders, final String key, final IOMessage payload) {
        this.handleInternal(key, payload);

        long count = messageCount.get();
        count++;
        messageCount.set(count);

        if ((logInterval > 0) && (count % logInterval == 0)) {
            // Logging...
        }
    }

    protected abstract void handleInternal(String key, IOMessage payload);

}
