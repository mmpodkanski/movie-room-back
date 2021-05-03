package io.github.mmpodkanski.actor.dto;

public interface ActorSimpleResponseDto {
    static ActorSimpleResponseDto create(final int id, final String firstName, final String lastName, final String imageUrl) {
        return new DeserializationImpl(id, firstName, lastName, imageUrl);
    }

    int getId();

    String getFirstName();

    String getLastName();

    String getImageUrl();

    class DeserializationImpl implements ActorSimpleResponseDto {
        private final int id;
        private final String firstName;
        private final String lastName;
        private final String imageUrl;


        public DeserializationImpl(final int id, final String firstName, final String lastName, final String imageUrl) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.imageUrl = imageUrl;
        }


        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getFirstName() {
            return firstName;
        }

        @Override
        public String getLastName() {
            return lastName;
        }

        @Override
        public String getImageUrl() {
            return imageUrl;
        }
    }
}
