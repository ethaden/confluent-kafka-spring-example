package org.example.spring.kafka.shared.kafka.error;

import jakarta.annotation.Nonnull;

import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;

import lombok.RequiredArgsConstructor;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.round;

@RequiredArgsConstructor
public class ExponentialBackOff implements BackOff {

    /**
     * Initial back off interval (in milliseconds)
     */
    private final long initialInterval;
    /**
     * Multiplier to calculate next interval. Set to 1 to get fixed back off interval
     */
    private final double multiplier;
    /**
     * Max intervall for retry (in milliseconds)
     */
    private final long maxInterval;
    /**
     * Max retry attempts. Set to value < 0 to get infinite retries
     */
    private final long maxAttempts;

    @Override
    @Nonnull
    public BackOffExecution start() {
        return new ExponentialBackOffExecution(max(initialInterval, 0), max(multiplier, 1), max(maxInterval, 0), maxAttempts);
    }

    private static class ExponentialBackOffExecution implements BackOffExecution {
        private final long initialInterval;
        private final double multiplier;
        private final long maxInterval;
        private final long maxAttempts;

        private long currentInterval;
        private long currentAttempts = 0L;

        private ExponentialBackOffExecution(final long initialInterval, final double multiplier, final long maxInterval, final long maxAttempts) {
            this.initialInterval = initialInterval;
            this.multiplier = multiplier;
            this.maxInterval = maxInterval;
            this.maxAttempts = maxAttempts;
            this.currentInterval = min(initialInterval, maxInterval);
        }

        @Override
        public long nextBackOff() {
            if (this.maxAttempts >= 0 && this.currentAttempts >= maxAttempts) {
                return STOP;
            }

            this.currentAttempts++;
            return computeNextInterval();
        }

        private long computeNextInterval() {
            if (this.currentInterval >= maxInterval) {
                return maxInterval;

            } else {
                this.currentInterval = min(round(this.initialInterval * pow(multiplier, currentAttempts - 1D)), maxInterval);
            }

            return this.currentInterval;
        }
    }
}
