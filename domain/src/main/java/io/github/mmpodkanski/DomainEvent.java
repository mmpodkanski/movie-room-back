package io.github.mmpodkanski;

import java.time.Instant;

public interface DomainEvent {
    Instant getOccurredOn();
}
