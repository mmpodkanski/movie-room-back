package io.github.mmpodkanski.movie;

import java.time.LocalDateTime;

class Comment {
    static Comment restore(CommentSnapshot snapshot) {
        return new Comment(
                snapshot.getId(),
                snapshot.getCreatedAt(),
                snapshot.getTitle(),
                snapshot.getDescription(),
                snapshot.getAuthor()
//                snapshot.getMovie() != null ? Movie.restore(snapshot.getMovie()) : null
        );
    }

    private final int id;
    private final LocalDateTime createdAt;
    private final String title;
    private final String description;
    private final String author;

    private Comment(
            final int id,
            final LocalDateTime createdAt,
            final String title,
            final String description,
            final String author
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    CommentSnapshot getSnapshot() {
        return new CommentSnapshot(
                id,
                createdAt,
                title,
                description,
                author
//                movie != null ? movie.getSnapshot() : null
        );
    }
}
