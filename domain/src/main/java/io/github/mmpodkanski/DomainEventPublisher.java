package io.github.mmpodkanski;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
