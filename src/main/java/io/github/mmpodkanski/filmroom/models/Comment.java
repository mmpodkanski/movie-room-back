package io.github.mmpodkanski.filmroom.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @NotBlank(message = "Title can not be empty !")
    private String title;
    @NotBlank(message = "Description can not be empty !")
    private String description;
    private String author;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Comment() {
    }

    public Comment(
            final String title,
            final String description,
            final String author,
            final User owner,
            final Movie movie
    ) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.owner = owner;
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    void setAuthor(final String author) {
        this.author = author;
    }

    User getOwner() {
        return owner;
    }

    void setOwner(final User owner) {
        this.owner = owner;
    }

    Movie getMovie() {
        return movie;
    }

    void setMovie(final Movie movie) {
        this.movie = movie;
    }
}
