package org.example.spring.kafka.integration.io.customEventData;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.example.spring.kafka.integration.kafka.model.IOMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomEventDataService {
    protected static final Logger logger = LogManager.getLogger(CustomEventDataService.class);

    @Transactional
    public void processEvent(final String key, final IOMessage payload) {
        // E.g. write to dead letter topic or external DB
        logger.warn("Consumed event: "+key+" with payload "+payload);
        if (payload.value().equals("error"))
        {
            logger.error("Error consuming event!!!");
            throw new RuntimeException("Unable to handle error event");
            // Note: This will just cause a rollback and then return as usual (which allows the application to commit the Kafka event)
        }
        if (payload.value().equals("crash"))
        {
            logger.error("Event is causing a crash! Crashing now...");
            System.exit(1);
            // Note: This will just cause a rollback and then return as usual (which allows the application to commit the Kafka event)
        }
    }
}
