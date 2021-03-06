package io.github.mmpodkanski.movieroom.models.request;

import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.ECategory;
import io.github.mmpodkanski.movieroom.models.Movie;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class MovieRequest {
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
    @Digits(integer = 4, fraction = 0, message = "Invalid date (expected: xxxx)")
    private String releaseDate;
    private String imgLogoUrl;
    private String imgBackUrl;

    public Movie toMovie(OffsetDateTime time, boolean createdByAdmin, Set<Actor> actors, ECategory category) {
        var movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setDirector(director);
        movie.setProducer(producer);
        movie.setReleaseDate(releaseDate);
        movie.setCreatedAt(time);
        movie.setAcceptedByAdmin(createdByAdmin);
        movie.setCategory(category);
        movie.addActors(actors);
        movie.setImgLogoUrl(imgLogoUrl);
        movie.setImgBackUrl(imgBackUrl);

        return movie;
    }

}
