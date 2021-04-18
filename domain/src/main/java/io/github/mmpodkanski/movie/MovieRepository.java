package io.github.mmpodkanski.movie;

import java.util.Optional;

interface MovieRepository {
    boolean existsByTitle(String title);

    Optional<Movie> findById(int id);

    Movie save(Movie entity);

    void delete(Movie entity);
}
