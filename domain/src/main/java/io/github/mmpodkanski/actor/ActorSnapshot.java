package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.movie.MovieSnapshot;

import java.util.HashSet;
import java.util.Set;

public class ActorSnapshot {
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String imageUrl;
    private Set<MovieSnapshot> movies = new HashSet<>();
    private boolean acceptedByAdmin;

    protected ActorSnapshot() {
    }

    public ActorSnapshot(
            final int id,
            final String firstName,
            final String lastName,
            final String birthDate,
            final String imageUrl,
            final Set<MovieSnapshot> movies,
            final boolean acceptedByAdmin
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.movies = movies;
        this.acceptedByAdmin = acceptedByAdmin;
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

    Set<MovieSnapshot> getMovies() {
        return movies;
    }

    boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }
}
