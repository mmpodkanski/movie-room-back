package io.github.mmpodkanski.movieroom.comment;

import io.github.mmpodkanski.movieroom.comment.dto.CommentRequestDto;
import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import io.github.mmpodkanski.movieroom.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class CommentFactory {

    Comment mapCommentDTO(CommentRequestDto commentRequestDto, MovieQueryDtoEntity movie, User owner) {
        var createdAt = LocalDateTime.now();
        var result = new Comment();

        result.setTitle(commentRequestDto.getTitle());
        result.setDescription(commentRequestDto.getDescription());
        result.setCreatedAt(createdAt);
        result.setAuthor(owner.getUsername());
        result.setOwner(owner);
        result.setMovie(movie);

        return result;
    }
}
