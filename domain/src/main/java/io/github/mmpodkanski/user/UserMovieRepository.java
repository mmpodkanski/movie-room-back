package io.github.mmpodkanski.user;


import java.util.Optional;

interface UserMovieRepository {
    Optional<UserMovie> findById(int id);


    UserMovie save(UserMovie userMovie);
}
