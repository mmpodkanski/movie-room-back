package io.github.mmpodkanski.movie.dto;

public class CommentRequestDto {
    private int id;
    private int ownerId;
    private String title;
    private String description;

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
