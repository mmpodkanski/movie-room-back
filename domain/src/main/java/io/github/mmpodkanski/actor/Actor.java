package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.movie.Movie;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Actor {
    public static Actor restore(ActorSnapshot snapshot) {
        return new Actor(
                snapshot.getId(),
                snapshot.getFirstName(),
                snapshot.getLastName(),
                snapshot.getBirthDate(),
                snapshot.getImageUrl(),
                snapshot.getMovies().stream().map(Movie::restore).collect(Collectors.toSet()),
                snapshot.isAcceptedByAdmin()
        );
    }

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate = "Not updated";
    private String imageUrl = "https://www.findcollab.com/img/user-folder/5d9704d04880fprofile.jpg";
    private Set<Movie> movies = new HashSet<>();
    private boolean acceptedByAdmin;

    protected Actor() {
    }

    private Actor(
            final int id,
            final String firstName,
            final String lastName,
            final String birthDate,
            final String imageUrl,
            final Set<Movie> movies,
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

    public ActorSnapshot getSnapshot() {
        return new ActorSnapshot(
                id,
                firstName,
                lastName,
                birthDate,
                imageUrl,
                movies.stream().map(Movie::getSnapshot).collect(Collectors.toSet()),
                acceptedByAdmin
        );
    }

    Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
