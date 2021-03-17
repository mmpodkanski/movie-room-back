package io.github.mmpodkanski.movieroom.movie.dto;

import io.github.mmpodkanski.movieroom.actor.dto.ActorSimpleResponseDto;
import io.github.mmpodkanski.movieroom.category.ECategory;
import io.github.mmpodkanski.movieroom.comment.dto.CommentResponseDto;

import java.util.List;

public interface MovieResponseDto {

    int getId();

    String getTitle();

    String getDescription();

    String getDirector();

    String getProducer();

    ECategory getCategory();

    String getReleaseDate();

    List<ActorSimpleResponseDto> getActors();

    List<CommentResponseDto> getComments();

    boolean isAcceptedByAdmin();

    String getImgLogoUrl();

    String getImgBackUrl();


}
