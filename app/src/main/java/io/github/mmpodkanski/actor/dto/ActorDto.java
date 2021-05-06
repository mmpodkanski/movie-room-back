package io.github.mmpodkanski.actor.dto;

import io.github.mmpodkanski.actor.EGender;

import java.util.Objects;

public class ActorDto {
    static public Builder builder() {
        return new Builder();
    }

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String imageUrl;
    private EGender gender;

    public ActorDto() {

    }

    public ActorDto(
            final int id,
            final String firstName,
            final String lastName,
            final String birthDate,
            final String imageUrl,
            final EGender gender
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.gender = gender;
    }

    public Builder toBuilder() {
        return new Builder()
                .withId(id)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withBirthDate(birthDate)
                .withImageUrl(imageUrl)
                .withGender(gender);
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

    public EGender getGender() {
        return gender;
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String birthDate;
        private String imageUrl;
        private EGender gender;

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

        public Builder withGender(EGender gender) {
            this.gender = gender;
            return this;
        }

        public ActorDto build() {
            return new ActorDto(id, firstName, lastName, birthDate, imageUrl, gender);
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
                Objects.equals(imageUrl, actorDto.imageUrl) &&
                Objects.equals(gender, actorDto.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, imageUrl, gender);
    }
}
