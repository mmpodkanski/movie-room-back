package io.github.mmpodkanski.movie.dto;

import javax.persistence.Column;
import java.time.LocalDateTime;

public interface CommentResponseDto {
    int getId();
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    LocalDateTime getCreatedAt();

    String geTitle();

    String getDescription();

    String getAuthor();
}
