package io.github.mmpodkanski.actor;

class Actor {
    public static Actor restore(ActorSnapshot snapshot) {
        return new Actor(
                snapshot.getId(),
                snapshot.getFirstName(),
                snapshot.getLastName(),
                snapshot.getBirthDate(),
                snapshot.getImageUrl(),
                snapshot.isAcceptedByAdmin(),
                snapshot.getGender()
        );
    }

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate = "Not updated";
    private String imageUrl;
    private boolean acceptedByAdmin;
    private EGender gender;

    protected Actor() {
    }

    private Actor(
            final int id,
            final String firstName,
            final String lastName,
            final String birthDate,
            final String imageUrl,
            final boolean acceptedByAdmin,
            final EGender gender
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.acceptedByAdmin = acceptedByAdmin;
        this.gender = gender;
    }


    public ActorSnapshot getSnapshot() {
        return new ActorSnapshot(
                id,
                firstName,
                lastName,
                birthDate,
                imageUrl,
                acceptedByAdmin,
                gender
        );
    }

    Actor(String firstName, String lastName, boolean createdByAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.acceptedByAdmin = createdByAdmin;
    }

    void setAcceptedByAdminToTrue() {
        this.acceptedByAdmin = true;
    }
}
