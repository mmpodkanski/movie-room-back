package io.github.mmpodkanski.filmroom.models.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDTO {
    @NotNull
    private int ownerId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
