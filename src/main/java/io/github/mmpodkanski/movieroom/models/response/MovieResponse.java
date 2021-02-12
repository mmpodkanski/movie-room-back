package io.github.mmpodkanski.movieroom.models.response;

import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.Comment;
import io.github.mmpodkanski.movieroom.models.Movie;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class MovieResponse {
    private int id;
    private String title;
    private String description;
    private String director;
    private String producer;
    private String category;
    private List<Actor> actors;
    private String releaseDate;
    private List<Comment> comments;

    public MovieResponse(Movie source) {
        id = source.getId();
        title = source.getTitle();
        description = source.getDescription();
        director = source.getDirector();
        producer = source.getProducer();
        category = source.getCategory().name();
        actors = new ArrayList<>(source.getActors());
        releaseDate = source.getReleaseDate();
        comments = new ArrayList<>(source.getComments());

        comments.sort(Comparator.comparing(Comment::getCreatedAt));
        actors.sort(Comparator.comparing(Actor::getFirstName));
    }
}

