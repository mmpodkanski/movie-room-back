package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsById(int id);

	boolean existsByUsername(String username);

    User save(User entity);

    void deleteById(int id);

    Optional<Object> findByUsernameIgnoringCase(String username);
}
