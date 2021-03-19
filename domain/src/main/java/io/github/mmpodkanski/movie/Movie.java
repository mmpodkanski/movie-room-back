package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.Actor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ACTORS_MOVIES",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private Set<Actor> actors = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private final Set<Comment> comments = new HashSet<>();
    private int stars;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    private boolean acceptedByAdmin;
    @Column(name = "imageLogoUrl")
    private String imgLogoUrl;
    @Column(name = "imageBackUrl")
    private String imgBackUrl;

    @PersistenceConstructor
    protected Movie() {
    }

//    MovieResponseDtoCopy toDto() {
//        return MovieResponseDtoCopy.builder()
//                .withId(id)
//                .withFirstName(firstName)
//                .withLastName(lastName)
//                .withBirthDate(birthDate)
//                .withImageUrl(imageUrl)
//                .build();
//    }

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

    int getId() {
        return id;
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

    String getDirector() {
        return director;
    }

    void setDirector(String director) {
        this.director = director;
    }

    String getProducer() {
        return producer;
    }

    void setProducer(String producer) {
        this.producer = producer;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    ECategory getCategory() {
        return category;
    }

    void setCategory(ECategory category) {
        this.category = category;
    }

    Set<Actor> getActors() {
        return actors;
    }

    List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    int getStars() {
        return stars;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }

    void setAcceptedByAdmin(boolean acceptedByAdmin) {
        this.acceptedByAdmin = acceptedByAdmin;
    }

    String getImgLogoUrl() {
        return imgLogoUrl;
    }

    void setImgLogoUrl(String imgLogoUrl) {
        this.imgLogoUrl = imgLogoUrl;
    }

    String getImgBackUrl() {
        return imgBackUrl;
    }

    void setImgBackUrl(String imgBackUrl) {
        this.imgBackUrl = imgBackUrl;
    }

    void update(Movie movie) {
        title = movie.getTitle();
        director = movie.getDirector();
        producer = movie.getProducer();
        description = movie.getDescription();
        releaseDate = movie.getReleaseDate();
        category = movie.getCategory();
        actors = movie.getActors();
        acceptedByAdmin = movie.isAcceptedByAdmin();
        imgLogoUrl = movie.getImgLogoUrl();
        imgBackUrl = movie.getImgBackUrl();
    }
}
