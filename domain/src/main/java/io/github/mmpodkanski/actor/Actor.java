package io.github.mmpodkanski.actor;

class Actor {
    public static Actor restore(ActorSnapshot snapshot) {
        return new Actor(
                snapshot.getId(),
                snapshot.getFirstName(),
                snapshot.getLastName(),
                snapshot.getBirthDate(),
                snapshot.getImageUrl(),
                snapshot.isAcceptedByAdmin()
        );
    }

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate = "Not updated";
    private String imageUrl;
    private boolean acceptedByAdmin;

    protected Actor() {
    }

    private Actor(
            final int id,
            final String firstName,
            final String lastName,
            final String birthDate,
            final String imageUrl,
            final boolean acceptedByAdmin
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.acceptedByAdmin = acceptedByAdmin;
    }


    public ActorSnapshot getSnapshot() {
        return new ActorSnapshot(
                id,
                firstName,
                lastName,
                birthDate,
                imageUrl,
                acceptedByAdmin
        );
    }

    Actor(String firstName, String lastName, boolean createdByAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.acceptedByAdmin = createdByAdmin;
    }
}
