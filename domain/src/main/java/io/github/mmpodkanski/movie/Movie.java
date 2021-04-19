package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.Actor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

class Movie {
    public static Movie restore(MovieSnapshot snapshot) {
        return new Movie(
                snapshot.getId(),
                snapshot.getTitle(),
                snapshot.getDirector(),
                snapshot.getProducer(),
                snapshot.getDescription(),
                snapshot.getReleaseDate(),
                snapshot.getCategory(),
                snapshot.getActors().stream().map(Actor::restore).collect(Collectors.toSet()),
                snapshot.getComments() != null ? snapshot.getComments().stream().map(Comment::restore).collect(Collectors.toSet()) : null,
                snapshot.getStars(),
                snapshot.getCreatedAt(),
                snapshot.isAcceptedByAdmin(),
                snapshot.getImgLogoUrl(),
                snapshot.getImgBackUrl()
        );
    }

    private final int id;
    private String title;
    private String director;
    private String producer;
    private String description;
    private String releaseDate;
    private ECategory category;
    private final Set<Actor> actors;
    private final Set<Comment> comments;
    private int stars;
    private final LocalDateTime createdAt;
    private boolean acceptedByAdmin;
    private String imgLogoUrl;
    private String imgBackUrl;

    private Movie(
            final int id,
            final String title,
            final String director,
            final String producer,
            final String description,
            final String releaseDate,
            final ECategory category,
            final Set<Actor> actors,
            final Set<Comment> comments,
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

    public MovieSnapshot getSnapshot() {
        return new MovieSnapshot(
                id,
                title,
                director,
                producer,
                description,
                releaseDate,
                category,
                actors != null ? actors.stream().map(Actor::getSnapshot).collect(Collectors.toSet()) : null,
                comments != null ? comments.stream().map(Comment::getSnapshot).collect(Collectors.toSet()) : null,
                stars,
                createdAt,
                acceptedByAdmin,
                imgLogoUrl,
                imgBackUrl
        );
    }

    void toggleStatus() {
        acceptedByAdmin = !acceptedByAdmin;
    }

    void addActors(Set<Actor> actors) {
        this.actors.addAll(actors);
    }

    void addComment(Comment commentToSave) {
        this.comments.add(commentToSave);
    }

    void removeComment(Comment commentToDelete) {
        this.comments.remove(commentToDelete);
    }

    void addStar() {
        ++stars;
    }

    void removeStar() {
//        if (stars < 1) {
//            throw new ApiBadRequestException("Movie doesn't has stars!");
//        }
        stars--;
    }

    void update(
            final String title,
            final String director,
            final String producer,
            final String description,
            final String releaseDate,
            final ECategory category,
            final String imgLogoUrl,
            final String imgBackUrl
    ) {
        this.title = title;
        this.director = director;
        this.producer = producer;
        this.description = description;
        this.releaseDate = releaseDate;
        this.category = category;
        this.acceptedByAdmin = true;
        this.imgLogoUrl = imgLogoUrl;
        this.imgBackUrl = imgBackUrl;
    }
}
