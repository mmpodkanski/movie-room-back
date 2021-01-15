package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();

    Optional<Movie> findById(int id);

    List<Movie> findAllByReleaseDate(String year);

    boolean existsById(int id);

    boolean existsByTitle(String title);

    Optional<Movie> findByTitle(String title);

    Movie save(Movie entity);

    void deleteById(int id);

    void deleteByTitle(String title);
}
