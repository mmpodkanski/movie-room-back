package io.github.mmpodkanski.actor;


class ActorSnapshot {
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String imageUrl;
    private boolean acceptedByAdmin;
    private EGender gender;

    protected ActorSnapshot() {
    }

    ActorSnapshot(
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

    int getId() {
        return id;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getBirthDate() {
        return birthDate;
    }

    String getImageUrl() {
        return imageUrl;
    }

    boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }

    EGender getGender() {
        return gender;
    }
}
