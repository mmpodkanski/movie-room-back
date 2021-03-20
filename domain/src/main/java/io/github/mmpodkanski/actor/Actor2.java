//package io.github.mmpodkanski.actor;
//
//import io.github.mmpodkanski.movie.Movie;
//import org.hibernate.annotations.GenericGenerator;
//import org.springframework.data.annotation.PersistenceConstructor;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "actors")
//public class Actor {
//    @Id
//    @GeneratedValue(generator = "inc")
//    @GenericGenerator(name = "inc", strategy = "increment")
//    private int id;
//    private String firstName;
//    private String lastName;
//    private String birthDate = "Not updated";
//    private String imageUrl = "https://www.findcollab.com/img/user-folder/5d9704d04880fprofile.jpg";
//    // TODO: create rating for every actor (ex. 1-10 -> avg)
//    @ManyToMany(mappedBy = "actors")
//    private final Set<Movie> movies = new HashSet<>();
//    private boolean acceptedByAdmin;
//
//    @PersistenceConstructor
//    protected Actor() {
//    }
//
//    Actor(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    int getId() {
//        return id;
//    }
//
//    void setId(final int id) {
//        this.id = id;
//    }
//
//    String getFirstName() {
//        return firstName;
//    }
//
//    void setFirstName(final String firstName) {
//        this.firstName = firstName;
//    }
//
//    String getLastName() {
//        return lastName;
//    }
//
//    void setLastName(final String lastName) {
//        this.lastName = lastName;
//    }
//
//    String getBirthDate() {
//        return birthDate;
//    }
//
//    void setBirthDate(final String birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    String getImageUrl() {
//        return imageUrl;
//    }
//
//    void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    Set<Movie> getMovies() {
//        return new HashSet<>(movies);
//    }
//
//    boolean isAcceptedByAdmin() {
//        return acceptedByAdmin;
//    }
//
//    void setAcceptedByAdmin(boolean acceptedByAdmin) {
//        this.acceptedByAdmin = acceptedByAdmin;
//    }
//}
