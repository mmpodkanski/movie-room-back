package io.github.mmpodkanski.filmroom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "award")
public class Award {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
//    @NotBlank(message = "Award's name must not be empty")
    private String name;
    private String description = "No updated";
    // TODO: aspect - count uses
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "awards", cascade = CascadeType.ALL)
    private Set<Movie> movies = new HashSet<>();

    public Award() {
    }

    public Award(final String name) {
        this.name = name;
    }

    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    @JsonIgnore
    Set<Movie> getMovies() {
        return movies;
    }

    void setMovies(final Set<Movie> movies) {
        this.movies = movies;
    }
}
