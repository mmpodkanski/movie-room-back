package io.github.mmpodkanski.actor.dto;

public class SimpleActorSnapshot {
    private int id;
    private String firstName;
    private String lastName;
    private String imageUrl;

    protected SimpleActorSnapshot() {
    }

    public SimpleActorSnapshot(
            final int id,
            final String firstName,
            final String lastName,
            final String imageUrl
    ) {
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
}
