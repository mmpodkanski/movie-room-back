package io.github.mmpodkanski.filmroom.entity;

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
//    @NotBlank(message = "Category name must not be empty")
    private String name;
    private boolean forAdults;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Movie> movies = new HashSet<>();

    public Category() {
    }

    public Category(final String name, final boolean forAdults) {
        this.name = name;
        this.forAdults = forAdults;
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

    public void setName(final String name) {
        this.name = name;
    }

    boolean isForAdults() {
        return forAdults;
    }

    void setForAdults(final boolean forAdults) {

        this.forAdults = forAdults;
    }

    @JsonIgnore
    public Set<Movie> getMovies() {
        return movies;
    }

    void setMovies(final Set<Movie> movies) {
        this.movies = movies;
    }
}
