package org.example.spring.kafka.shared.kafka.error;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.transaction.TransactionException;

import java.sql.SQLRecoverableException;

import static java.lang.String.valueOf;
import static org.springframework.util.backoff.BackOffExecution.STOP;

@SuppressWarnings("java:S1699") //Constructors should only call non-overridable methods
public class KafkaErrorHandler extends DefaultErrorHandler {

    public KafkaErrorHandler(final DeadLetterService deadLetterService) {

        // If BackOff function returns "STOP", write event to dead letter queue and continue
        super((consumerRecord, exception) -> {
            deadLetterService.writeDeadLetterEntry(consumerRecord.topic(), valueOf(consumerRecord.key()), consumerRecord.value() != null ? valueOf(consumerRecord.value()) : null,
                exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage());
        });

        setBackOffFunction(
            (consumerRecord, exception) -> {
                // Retry processing. Queue is blocked, but no event is lost
                if (exception instanceof DataAccessResourceFailureException // z.B. CannotGetJdbcConnectionException
                    || exception instanceof TransactionException // z.B. CannotCreateTransactionException
                    || exception instanceof SQLRecoverableException
                    || exception instanceof RecoverableDataAccessException
                    || exception instanceof KafkaException) { // z.B. ListenerExecutionFailedException
                    return new ExponentialBackOff(50, 1.2, 10 * 1000L, -1);
                }
                // In case of a permanent error, write to dead letter queue instead.
                // The events in the DLQ need to be processed separately
                return () -> () -> STOP;
            });

        setRetryListeners((consumerRecord, ex, deliveryAttempt) -> {
            if (deliveryAttempt > 1) {
                // Logging...
            }
        });
        setCommitRecovered(true);
    }
}
