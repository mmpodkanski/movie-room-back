package io.github.mmpodkanski.movieroom.repository;

import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByFavourites(Movie movie);

    boolean existsByIdAndFavourites(int id, Movie movie);

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    User save(User entity);
}
