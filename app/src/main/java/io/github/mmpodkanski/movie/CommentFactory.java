package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class CommentFactory {

    Comment mapCommentDTO(CommentRequestDto source, Movie movie, User owner) {
        var createdAt = LocalDateTime.now();

        return Comment.restore(new CommentSnapshot(
                source.getId(),
                createdAt,
                source.getTitle(),
                source.getDescription(),
                owner.getUsername(),
                owner,
                movie.getSnapshot()
        ));
    }
}
