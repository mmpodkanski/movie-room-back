package io.github.mmpodkanski.actor.dto;

public class ActorSimpleRequestDto {
    private int id;
    private String firstName;
    private String lastName;

    public ActorSimpleRequestDto(){
    }

    public ActorSimpleRequestDto(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(String firstName, String lastName) {
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
