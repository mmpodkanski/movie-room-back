package io.github.mmpodkanski.movie;

import java.util.Optional;

interface MovieRepository {
    Optional<Movie> findById(int id);

    Movie save(Movie entity);

    void delete(Movie entity);
}
