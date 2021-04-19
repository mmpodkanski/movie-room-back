package io.github.mmpodkanski.auth;

import io.github.mmpodkanski.auth.dto.UserDto;

import java.util.List;
import java.util.Optional;

interface UserQueryRepository {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<UserDto> findAll();

    Optional<UserDto> findByUsername(String username);

}
