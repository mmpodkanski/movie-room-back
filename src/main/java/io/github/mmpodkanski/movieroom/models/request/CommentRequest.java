package io.github.mmpodkanski.movieroom.models.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequest {
    @NotNull
    private int ownerId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
