package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.CommentResponseDto;

import java.util.List;
import java.util.Optional;

interface CommentQueryRepository {

    boolean existsByTitle(String title);

    List<CommentResponseDto> findAllBy();

    Optional<CommentResponseDto> findDtoById(int id);

}
