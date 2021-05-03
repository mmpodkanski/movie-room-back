package io.github.mmpodkanski.actor.dto;

public class SimpleActor {
    public static SimpleActor restore(final SimpleActorSnapshot snapshot) {
        return new SimpleActor(snapshot.getId(), snapshot.getFirstName(), snapshot.getLastName(), snapshot.getImageUrl());
    }

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String imageUrl;

    public SimpleActor(final int id, final String firstName, final String lastName, final String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public SimpleActorSnapshot getSnapshot() {
        return new SimpleActorSnapshot(id, firstName, lastName, imageUrl);
    }
}
