package io.github.mmpodkanski.movieroom.actor;

import io.github.mmpodkanski.movieroom.actor.dto.ActorRequestDto;
import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.PersistenceConstructor;

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
    private String imageUrl = "https://www.findcollab.com/img/user-folder/5d9704d04880fprofile.jpg";
    // TODO: create rating for every actor (ex. 1-10 -> avg)
    @ManyToMany(mappedBy = "actors")
    private final Set<MovieQueryDtoEntity> movies = new HashSet<>();
//    private boolean acceptedByAdmin;

    @PersistenceConstructor
    protected Actor() {
    }

    Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    ActorRequestDto toDto() {
        return ActorRequestDto.builder()
                .withId(id)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withBirthDate(birthDate)
                .withImageUrl(imageUrl)
                .build();
    }

    void update(ActorRequestDto actor) {
        firstName = actor.getFirstName();
        lastName = actor.getLastName();
        birthDate = actor.getBirthDate();
        imageUrl = actor.getImageUrl();
    }

    public int getId() {
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

    public Set<MovieQueryDtoEntity> getMovies() {
        return new HashSet<>(movies);
    }
}
