package io.github.mmpodkanski.movie.dto;

import io.github.mmpodkanski.actor.dto.ActorSimpleRequestDto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class MovieRequestDto {
    private int id;
    @NotBlank(message = "Movie's title can not be empty!")
    private String title;
    @NotBlank(message = "Movie's description can not be empty!")
    private String description;
    @NotBlank(message = "Movie's director can not be empty!")
    private String director;
    @NotBlank(message = "Movie's producer can not be empty!")
    private String writer;
    @NotBlank(message = "Movie's category can not be empty!")
    private String category;
    @NotEmpty(message = "Please add some actors!")
    private Set<ActorSimpleRequestDto> actors;
    @Digits(integer = 4, fraction = 0, message = "Invalid date (expected: xxxx)")
    private String releaseDate;
    private String imgLogoUrl;
    private String imgBackUrl;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getCategory() {
        return category;
    }

    public Set<ActorSimpleRequestDto> getActors() {
        return actors;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }

    public String getImgBackUrl() {
        return imgBackUrl;
    }

}