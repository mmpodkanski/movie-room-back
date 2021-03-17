package io.github.mmpodkanski.movieroom.user;

import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends Repository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByFavourites(MovieQueryDtoEntity movie);

    boolean existsByIdAndFavourites(int id, MovieQueryDtoEntity movie);

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    User save(User entity);
}
