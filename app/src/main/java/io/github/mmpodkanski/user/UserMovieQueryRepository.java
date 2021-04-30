package io.github.mmpodkanski.user;

import io.github.mmpodkanski.user.dto.UserMovieDto;

import java.util.List;

interface UserMovieQueryRepository {
    List<UserMovieDto> findAllById(int id);
}
