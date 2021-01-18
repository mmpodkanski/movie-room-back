package io.github.mmpodkanski.filmroom.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@Data
public class Movie {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @NotBlank(message = "Movie's title must not be empty")
    private String title;
    @NotBlank(message = "Movie's description must not be empty")
    private String description;
    @NotBlank(message = "Movie has to have director")
    private String director;
    private String producer;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private OffsetDateTime createdAt;
    @Digits(integer = 4, fraction = 0, message = "Invalid date (expected: xxxx)")
    private String releaseDate;
    @Enumerated(EnumType.STRING)
    private ECategory category;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "actors_id")
    private Set<Actor> actors = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "awards_id")
    private Set<Award> awards = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
//    @JoinTable(name = "movie_comments",
//            joinColumns = @JoinColumn(name = "movie_id"),
//            inverseJoinColumns = @JoinColumn(name = "comment_id")
//    )
    private Set<Comment> comments;


}
