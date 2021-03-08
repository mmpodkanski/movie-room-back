package io.github.mmpodkanski.movieroom.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @NotBlank(message = "Movie's title must not be empty")
    private String title;
    @NotBlank(message = "Movie has to have director")
    private String director;
    private String producer;
    @NotBlank(message = "Movie's description must not be empty")
    private String description;
    @Digits(integer = 4, fraction = 0, message = "Invalid date (expected: xxxx)")
    private String releaseDate;
    @Enumerated(EnumType.STRING)
    private ECategory category;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "actors_id")
    private Set<Actor> actors = new HashSet<>();
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private Set<Comment> comments = new HashSet<>();
    private int stars;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private OffsetDateTime createdAt;
    private boolean acceptedByAdmin;
    @Column(name = "imageLogoUrl")
    private String imgLogoUrl;
    @Column(name = "imageBackUrl")
    private String imgBackUrl;

    public Movie() {
    }

    public void addActors(Set<Actor> actors) {
        this.actors.addAll(actors);
    }

    public void addComment(Comment commentToSave) {
        this.comments.add(commentToSave);
    }

    public void removeComment(Comment commentToDelete) {
        this.comments.remove(commentToDelete);
    }

    void addStar() {
        ++stars;
    }

    void removeStar() {
        if (stars < 1) {
            throw new ApiBadRequestException("Movie doesn't has stars!");
        }
        --stars;
    }

    public void update(Movie movie) {
        title = movie.getTitle();
        director = movie.getDirector();
        producer = movie.getProducer();
        description = movie.getDescription();
        releaseDate = movie.getReleaseDate();
        category = movie.getCategory();
//        actors = movie.getActors();
        acceptedByAdmin = movie.isAcceptedByAdmin();
        imgLogoUrl = movie.getImgLogoUrl();
        imgBackUrl = movie.getImgBackUrl();
    }
}
