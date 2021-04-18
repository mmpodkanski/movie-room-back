package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.ActorSnapshot;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class MovieSnapshot {
    private int id;
    private String title;
    private String director;
    private String producer;
    private String description;
    private String releaseDate;
    private ECategory category;
    private Set<ActorSnapshot> actors = new HashSet<>();
    private Set<CommentSnapshot> comments = new HashSet<>();
    private int stars;
    private LocalDateTime createdAt;
    private boolean acceptedByAdmin;
    private String imgLogoUrl;
    private String imgBackUrl;

    public MovieSnapshot() {
    }

    MovieSnapshot(
            final int id,
            final String title,
            final String director,
            final String producer,
            final String description,
            final String releaseDate,
            final ECategory category,
            final Set<ActorSnapshot> actors,
            final Set<CommentSnapshot> comments,
            final int stars,
            final LocalDateTime createdAt,
            final boolean acceptedByAdmin,
            final String imgLogoUrl,
            final String imgBackUrl
    ) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.producer = producer;
        this.description = description;
        this.releaseDate = releaseDate;
        this.category = category;
        this.actors = actors;
        this.comments = comments;
        this.stars = stars;
        this.createdAt = createdAt;
        this.acceptedByAdmin = acceptedByAdmin;
        this.imgLogoUrl = imgLogoUrl;
        this.imgBackUrl = imgBackUrl;
    }

    int getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getDirector() {
        return director;
    }

    String getProducer() {
        return producer;
    }

    String getDescription() {
        return description;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    ECategory getCategory() {
        return category;
    }

    Set<ActorSnapshot> getActors() {
        return actors;
    }

    Set<CommentSnapshot> getComments() {
        return comments;
    }

    int getStars() {
        return stars;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }

    String getImgLogoUrl() {
        return imgLogoUrl;
    }

    String getImgBackUrl() {
        return imgBackUrl;
    }
}
