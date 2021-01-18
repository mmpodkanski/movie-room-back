package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    boolean existsById(int id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User entity);
}
