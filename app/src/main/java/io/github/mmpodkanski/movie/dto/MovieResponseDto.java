package io.github.mmpodkanski.movie.dto;

import io.github.mmpodkanski.actor.dto.ActorSimpleResponseDto;
import io.github.mmpodkanski.movie.ECategory;

import java.util.List;

public interface MovieResponseDto {

    int getId();

    String getTitle();

    String getDescription();

    String getDirector();

    String getProducer();

    ECategory getCategory();

    String getReleaseDate();

    int getStars();

    List<ActorSimpleResponseDto> getActors();

    List<CommentResponseDto> getComments();

    boolean isAcceptedByAdmin();

    String getImgLogoUrl();

    String getImgBackUrl();

}
