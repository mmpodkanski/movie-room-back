package io.github.mmpodkanski.movieroom.actor.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ActorRequestDto.Builder.class)
public class ActorRequestDto {
    static public Builder builder() {
        return new Builder();
    }

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String imageUrl;
//    private final boolean acceptedByAdmin;

    ActorRequestDto(final Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.imageUrl = builder.imageUrl;
//        this.acceptedByAdmin = builder.acceptedByAdmin;
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

    @JsonPOJOBuilder
    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String birthDate;
        private String imageUrl;
//        private boolean acceptedByAdmin;

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
//
//        public Builder withAcceptedByAdmin(boolean acceptedByAdmin) {
//            this.acceptedByAdmin = acceptedByAdmin;
//            return this;
//        }

        public ActorRequestDto build() {
            return new ActorRequestDto(this);
        }

    }

}
