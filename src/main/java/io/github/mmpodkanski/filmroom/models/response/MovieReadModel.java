package io.github.mmpodkanski.filmroom.models.response;

import io.github.mmpodkanski.filmroom.models.Actor;
import io.github.mmpodkanski.filmroom.models.Movie;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MovieReadModel {
    private String title;
    private String description;
    private String director;
    private String producer;
    private String category;
    // TODO: maybe ActorReadModel
    private Set<Actor> actors = new HashSet<>();
    //    private Set<AwardWriteModel> awards = new HashSet<>();
    private String releaseDate;

    public MovieReadModel(Movie source) {
        title = source.getTitle();
        description = source.getDescription();
        director = source.getDirector();
        producer = source.getProducer();
        category = source.getCategory().getName();
        actors.addAll(source.getActors());
        releaseDate = source.getReleaseDate();
    }
}

