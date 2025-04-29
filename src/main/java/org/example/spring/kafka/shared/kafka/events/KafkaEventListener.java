package org.example.spring.kafka.shared.kafka.events;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.ConsumerStartedEvent;
import org.springframework.kafka.event.ConsumerStoppedEvent;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {
    @EventListener
    public void event(ConsumerStartedEvent consumerStartedEvent) {
        final ContainerProperties containerProperties = ((MessageListenerContainer) consumerStartedEvent.getSource()).getContainerProperties();
        // Logging ...
    }

    @EventListener
    public void event(ConsumerStoppedEvent consumerStoppedEvent) {
        final ContainerProperties containerProperties = ((MessageListenerContainer) consumerStoppedEvent.getSource()).getContainerProperties();
        // Logging ...
    }
}
