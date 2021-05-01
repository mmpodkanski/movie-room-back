package io.github.mmpodkanski.user;

import java.util.List;
import java.util.Optional;

interface UserRepository {

    List<User> findAllByFavouritesContaining(UserMovie userMovie);

    Optional<User> findById(int id);

    User save(User entity);

    void delete(User entity);

}
