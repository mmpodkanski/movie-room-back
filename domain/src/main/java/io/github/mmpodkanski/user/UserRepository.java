package io.github.mmpodkanski.user;

import java.util.Optional;

interface UserRepository {

    Optional<User> findById(int id);

    User save(User entity);

    void delete(User entity);

}
