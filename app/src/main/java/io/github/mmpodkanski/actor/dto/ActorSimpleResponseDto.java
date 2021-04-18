package io.github.mmpodkanski.actor.dto;

public interface ActorSimpleResponseDto {
    static ActorSimpleResponseDto create(final int id, final String firstName, final String lastName) {
        return new DeserializationImpl(id, firstName, lastName);
    }

    int getId();

    String getFirstName();

    String getLastName();

    class DeserializationImpl implements ActorSimpleResponseDto {
        private final int id;
        private final String firstName;
        private final String lastName;


        public DeserializationImpl(final int id, final String firstName, final String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
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
    }
}
