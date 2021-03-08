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
    private String releaseDate;
    private List<Actor> actors;
    private List<Comment> comments;
    private boolean acceptedByAdmin;
    private String imgLogoUrl;
    private String imgBackUrl;

    public MovieResponse(Movie source) {
        id = source.getId();
        title = source.getTitle();
        description = source.getDescription();
        director = source.getDirector();
        producer = source.getProducer();
        category = source.getCategory().name();
        releaseDate = source.getReleaseDate();
        actors = new ArrayList<>(source.getActors());
        comments = new ArrayList<>(source.getComments());
        acceptedByAdmin = source.isAcceptedByAdmin();
        imgLogoUrl = source.getImgLogoUrl();
        imgBackUrl = source.getImgBackUrl();

        comments.sort(Comparator.comparing(Comment::getCreatedAt));
        actors.sort(Comparator.comparing(Actor::getFirstName));
    }
}

