package io.github.mmpodkanski.actor.dto;

public class SimpleActor {
    public static SimpleActor restore(final SimpleActorSnapshot snapshot) {
        return new SimpleActor(snapshot.getId(), snapshot.getFirstName(), snapshot.getLastName());
    }

    private final int id;
    private final String firstName;
    private final String lastName;

    public SimpleActor(final int id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SimpleActorSnapshot getSnapshot() {
        return new SimpleActorSnapshot(id,firstName, lastName);
    }
}
