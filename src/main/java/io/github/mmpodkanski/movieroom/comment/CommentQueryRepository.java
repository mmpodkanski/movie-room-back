package io.github.mmpodkanski.movieroom.comment;

import io.github.mmpodkanski.movieroom.comment.dto.CommentResponseDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface CommentQueryRepository extends Repository<Comment, Integer> {
    boolean existsByTitle(String title);

    Optional<CommentResponseDto> findBy();

    List<CommentResponseDto> findAllBy();

}
