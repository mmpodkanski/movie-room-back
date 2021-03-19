package io.github.mmpodkanski.actor.dto;

import java.util.Objects;

public class ActorDto {
    static public Builder builder() {
        return new Builder();
    }

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String imageUrl;

    public ActorDto(final int id, final String firstName, final String lastName, String birthDate, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
    }

    public Builder toBuilder() {
        return new Builder()
                .withId(id)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withBirthDate(birthDate)
                .withImageUrl(imageUrl);
//                .withAcceptedByAdmin(acceptedByAdmin);
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

    public String getBirthDate() {
        return birthDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String birthDate;
        private String imageUrl;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ActorDto build() {
            return new ActorDto(id, firstName, lastName, birthDate, imageUrl);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDto actorDto = (ActorDto) o;
        return id == actorDto.id &&
                Objects.equals(firstName, actorDto.firstName) &&
                Objects.equals(lastName, actorDto.lastName) &&
                Objects.equals(birthDate, actorDto.birthDate) &&
                Objects.equals(imageUrl, actorDto.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, imageUrl);
    }
}
