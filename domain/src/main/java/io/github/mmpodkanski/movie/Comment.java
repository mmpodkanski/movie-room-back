package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
class Comment {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
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

    @PersistenceConstructor
    protected Comment() {
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    User getOwner() {
        return owner;
    }

    void setOwner(User owner) {
        this.owner = owner;
    }

    Movie getMovie() {
        return movie;
    }

    void setMovie(Movie movie) {
        this.movie = movie;
    }

}
