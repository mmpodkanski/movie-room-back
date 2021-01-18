package io.github.mmpodkanski.filmroom.models.request;

import io.github.mmpodkanski.filmroom.models.Movie;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class MovieWriteModel {
    @NotBlank(message = "Movie's title can not be empty!")
    private String title;
    @NotBlank(message = "Movie's description can not be empty!")
    private String description;
    @NotBlank(message = "Movie's director can not be empty!")
    private String director;
    @NotBlank(message = "Movie's producer can not be empty!")
    private String producer;
    @NotBlank(message = "Movie's category can not be empty!")
    private String category;
    @NotEmpty(message = "Please add some actors!")
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
