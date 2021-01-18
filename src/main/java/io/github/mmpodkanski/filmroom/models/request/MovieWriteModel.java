package io.github.mmpodkanski.filmroom.models.request;

import io.github.mmpodkanski.filmroom.models.Movie;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class MovieWriteModel {
    @NotBlank(message = "Movie's title must not be empty")
    private String title;
    @NotBlank(message = "Movie's description must not be empty")
    private String description;
    @NotBlank(message = "Movie's director must not be empty ")
    private String director;
    @NotBlank(message = "Movie's producer must not be empty ")
    private String producer;
    private Set<String> categories = new HashSet<>();
    private Set<String> actors = new HashSet<>();
    //    private Set<AwardWriteModel> awards = new HashSet<>();
    @Digits(integer = 4, fraction = 0, message = "Invalid date (expected: xxxx)")
    private String releaseDate;

    public Movie toMovie(OffsetDateTime time) {
        var movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setDirector(director);
        movie.setProducer(producer);
        movie.setReleaseDate(releaseDate);
        movie.setCreatedAt(time);
        return movie;
    }

}
