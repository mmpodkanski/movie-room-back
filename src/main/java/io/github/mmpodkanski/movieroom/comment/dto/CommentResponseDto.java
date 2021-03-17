package io.github.mmpodkanski.movieroom.comment.dto;

import javax.persistence.Column;
import java.time.LocalDateTime;

public interface CommentResponseDto {
    int getId();

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    LocalDateTime getCreatedAt();

    String geTitle();

    @Column(name = "description")
    String getDescription();

    String getAuthor();
}
