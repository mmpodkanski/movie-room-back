package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.user.User;

import java.time.LocalDateTime;

class CommentSnapshot {
    private int id;
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private String author;
    private User owner;

    protected CommentSnapshot() {
    }

    CommentSnapshot(
            final int id,
            final LocalDateTime createdAt,
            final String title,
            final String description,
            final String author,
            final User owner
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.author = author;
        this.owner = owner;
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

    User getOwner() {
        return owner;
    }

}
