package io.github.mmpodkanski.movie;

import java.time.LocalDateTime;

class CommentSnapshot {
    private int id;
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private String author;

    public CommentSnapshot() {
    }

    CommentSnapshot(
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

    int getId() {
        return id;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    String getAuthor() {
        return author;
    }


}
