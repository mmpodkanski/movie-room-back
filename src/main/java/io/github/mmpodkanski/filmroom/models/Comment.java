package io.github.mmpodkanski.filmroom.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
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
}
