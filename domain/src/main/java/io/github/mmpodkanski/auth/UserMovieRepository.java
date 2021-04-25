package io.github.mmpodkanski.auth;

import java.util.Optional;

interface UserMovieRepository {
    Optional<UserMovie> findById(int id);

    UserMovie save(UserMovie userMovie);
}
