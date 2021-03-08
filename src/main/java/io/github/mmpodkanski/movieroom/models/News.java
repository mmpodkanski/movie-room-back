package io.github.mmpodkanski.movieroom.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class News {
    private String author;
    private String title;
    private String description;
    private String releaseDate;
    private String imageUrl;
    private String url;
}
