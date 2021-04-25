package io.github.mmpodkanski.actor.dto;

public class SimpleActorSnapshot {
    private int id;
    private String firstName;
    private String lastName;

    protected SimpleActorSnapshot() {
    }

    public SimpleActorSnapshot(final int id, final String firstName, final String lastName) {
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

}
