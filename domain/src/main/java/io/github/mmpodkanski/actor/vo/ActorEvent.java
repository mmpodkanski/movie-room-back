package io.github.mmpodkanski.actor.vo;

import io.github.mmpodkanski.DomainEvent;

import java.time.Instant;
import java.util.Optional;

public class ActorEvent implements DomainEvent {
    private final Instant occurredOn;
    private final ActorId id;
    private final Data data;

    public ActorEvent(final ActorId id) {
        this(id, null);
    }

    public ActorEvent(final ActorId id, final Data data) {
        this.id = id;
        this.data = data;
        this.occurredOn = Instant.now();
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public Optional<ActorId> getId() {
        return Optional.ofNullable(id);
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private final String firstName;
        private final String lastName;
        private final String imageUrl;

        public Data(final String firstName,final  String lastName, final String imageUrl) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.imageUrl = imageUrl;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

}
