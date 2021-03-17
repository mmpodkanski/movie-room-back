package io.github.mmpodkanski.movieroom.movie;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface MovieRepository extends Repository<Movie, Integer> {
    boolean existsByTitle(String title);

    Optional<Movie> findById(int id);

    Movie save(Movie entity);

    void delete(Movie entity);
}
