package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class CommentFactory {

    Comment mapCommentDTO(CommentRequestDto source, String owner) {
        var createdAt = LocalDateTime.now();

        return Comment.restore(new CommentSnapshot(
                source.getId(),
                createdAt,
                source.getTitle(),
                source.getDescription(),
                owner
        ));
    }
}
