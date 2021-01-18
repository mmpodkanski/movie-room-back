package io.github.mmpodkanski.filmroom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @Enumerated(EnumType.STRING)
    private ECategory name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Movie> movies = new HashSet<>();

    public Category() {
    }

    public Category(final ECategory name) {
        this.name = name;
    }

    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name.toString();
    }


    @JsonIgnore
    public Set<Movie> getMovies() {
        return movies;
    }

    void setMovies(final Set<Movie> movies) {
        this.movies = movies;
    }
}