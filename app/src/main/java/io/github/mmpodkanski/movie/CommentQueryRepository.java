package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.CommentResponseDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface CommentQueryRepository extends Repository<Comment, Integer> {

    boolean existsByTitle(String title);

    List<CommentResponseDto> findAllBy();

    Optional<CommentResponseDto> findDtoById(int id);

}
