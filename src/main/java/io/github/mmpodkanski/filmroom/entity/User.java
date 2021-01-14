package io.github.mmpodkanski.filmroom.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private String token;
    private String role;
    private boolean accountVerified;
    private boolean enabled = true;

//    @OneToMany(mappedBy = "user")
//    private Set<SecureToken> tokens;
}
