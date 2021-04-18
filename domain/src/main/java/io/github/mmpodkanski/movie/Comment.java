package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.user.User;

import java.time.LocalDateTime;

class Comment {
    static Comment restore(CommentSnapshot snapshot) {
        return new Comment(
                snapshot.getId(),
                snapshot.getCreatedAt(),
                snapshot.getTitle(),
                snapshot.getDescription(),
                snapshot.getAuthor(),
                snapshot.getOwner(),
                snapshot.getMovie() != null ? Movie.restore(snapshot.getMovie()) : null
        );
    }

    private final int id;
    private final LocalDateTime createdAt;
    private final String title;
    private final String description;
    private final String author;
    private final User owner;
    private final Movie movie;

    private Comment(
            final int id,
            final LocalDateTime createdAt,
            final String title,
            final String description,
            final String author,
            final User owner,
            final Movie movie
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.author = author;
        this.owner = owner;
        this.movie = movie;
    }

    CommentSnapshot getSnapshot() {
        return new CommentSnapshot(
                id,
                createdAt,
                title,
                description,
                author,
                owner,
                movie != null ? movie.getSnapshot() : null
        );
    }
}
