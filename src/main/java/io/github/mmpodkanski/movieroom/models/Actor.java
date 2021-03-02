package io.github.mmpodkanski.movieroom.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate = "Not updated";
    private String imageUrl;
    // TODO: create rating for every actor (ex. 1-10 -> avg)
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "actors")
    private final Set<Movie> movies = new HashSet<>();

    public Actor() {
    }

    public Actor(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    // TODO: add getters and setters to other entities or use lombok

    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    Set<Movie> getMovies() {
        return new HashSet<>(movies);
    }
}
