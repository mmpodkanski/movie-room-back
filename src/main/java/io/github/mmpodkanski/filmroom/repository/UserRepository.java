package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.models.User;
import io.github.mmpodkanski.filmroom.models.response.MovieReadModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    boolean existsById(int id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User entity);
}
