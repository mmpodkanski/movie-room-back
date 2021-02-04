package io.github.mmpodkanski.movieroom.models.response;

import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.Comment;
import io.github.mmpodkanski.movieroom.models.Movie;
import lombok.Data;

import java.util.Set;

@Data
public class MovieReadModel {
    private int id;
    private String title;
    private String description;
    private String director;
    private String producer;
    private String category;
    // TODO: maybe ActorReadModel
    private Set<Actor> actors;
    private String releaseDate;
    private Set<Comment> comments;

    public MovieReadModel(Movie source) {
        id = source.getId();
        title = source.getTitle();
        description = source.getDescription();
        director = source.getDirector();
        producer = source.getProducer();
        category = source.getCategory().name();
        actors = source.getActors();
        releaseDate = source.getReleaseDate();
        comments = source.getComments();
    }
}

