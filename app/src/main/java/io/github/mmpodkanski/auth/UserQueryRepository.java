package io.github.mmpodkanski.auth;

import java.util.List;
import java.util.Optional;

interface UserQueryRepository {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findAll();

    Optional<User> findByUsername(String username);

}
