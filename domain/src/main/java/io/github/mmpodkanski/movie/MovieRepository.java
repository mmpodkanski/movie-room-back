package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.dto.SimpleActor;

import java.util.List;
import java.util.Optional;

interface MovieRepository {
    Optional<Movie> findById(int id);

    Movie save(Movie entity);

    void delete(Movie entity);

    List<Movie> findAllMoviesByActorsContains(SimpleActor actor);
}
