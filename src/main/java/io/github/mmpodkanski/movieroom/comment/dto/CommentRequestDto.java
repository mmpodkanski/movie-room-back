package io.github.mmpodkanski.movieroom.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequestDto {
    @NotNull
    private int ownerId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
