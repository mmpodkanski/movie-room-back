package io.github.mmpodkanski.movieroom.repository;

import io.github.mmpodkanski.movieroom.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    boolean existsById(int id);

    boolean existsByTitle(String title);

    List<Movie> findAll();

    List<Movie> findAllByReleaseDate(String year);

    Optional<Movie> findById(int id);

    Optional<Movie> findByTitle(String title);

    Movie save(Movie entity);

    void deleteById(int id);

    void deleteByTitle(String title);
}
