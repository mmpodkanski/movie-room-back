package io.github.mmpodkanski.user;

import io.github.mmpodkanski.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

interface UserQueryRepository {

    boolean existsByIdAndFavourites(int id, UserMovie movie);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<UserDto> findAllBy();

    Optional<UserDto> findByUsername(String username);

}
