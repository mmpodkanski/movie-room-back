package io.github.mmpodkanski.movie.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequestDto {
    @NotNull
    private int ownerId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    public int getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
