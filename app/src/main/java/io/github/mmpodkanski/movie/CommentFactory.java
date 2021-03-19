package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class CommentFactory {

    Comment mapCommentDTO(CommentRequestDto commentRequestDto, Movie movie, User owner) {
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
