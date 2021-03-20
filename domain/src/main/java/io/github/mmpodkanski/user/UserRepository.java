package io.github.mmpodkanski.user;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    boolean existsByFavourites(Movie movie);
//
//    boolean existsByIdAndFavourites(int id, Movie movie);

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    User save(User entity);
}
