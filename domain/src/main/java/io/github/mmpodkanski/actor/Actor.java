package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.movie.Movie;
import io.github.mmpodkanski.movie.MovieSnapshot;

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
                //FIXME: WTF
                snapshot.getMovies(),
                snapshot.isAcceptedByAdmin()
        );
    }

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate = "Not updated";
    private String imageUrl;
    private final Set<Movie> movies = new HashSet<>();
    private boolean acceptedByAdmin;

    protected Actor() {
    }

    private Actor(
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
        modifyMovies(movies);
        this.acceptedByAdmin = acceptedByAdmin;
    }

    private void modifyMovies(final Set<MovieSnapshot> init) {
        Set<Movie> newMovies = new HashSet<>();

        for (MovieSnapshot movieSnapshot : init) {
            newMovies.add(Movie.restore(movieSnapshot));
        }

        movies.addAll(newMovies);
    }

    public ActorSnapshot getSnapshot() {
        return new ActorSnapshot(
                id,
                firstName,
                lastName,
                birthDate,
                imageUrl,
                movies.size() > 0 ? movies.stream().map(Movie::getSnapshot).collect(Collectors.toSet()) : null,
                acceptedByAdmin
        );
    }

    Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
