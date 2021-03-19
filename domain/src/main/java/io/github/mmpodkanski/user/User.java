package io.github.mmpodkanski.user;

import io.github.mmpodkanski.movie.Movie;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private ERole role;
    private boolean locked;
    private boolean enabled = true;
    // FIXME: CANT DELETE PARENT
    @OneToMany
    private Set<Movie> favourites = new HashSet<>();

    @PersistenceConstructor
    public User() {
    }

    public User(
            @NotBlank final String username,
            @NotBlank @Email final String email,
            @NotBlank final String password
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    void addFavourite(Movie movie) {
        favourites.add(movie);
    }

    void removeFavourite(Movie movie) {
//        if (favourites.isEmpty()) {
//            throw new ApiBadRequestException("User doesn't has favourites!");
//        }

        favourites.remove(movie);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    boolean isLocked() {
        return locked;
    }

    void setLocked(boolean locked) {
        this.locked = locked;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    Set<Movie> getFavourites() {
        return favourites;
    }

    void setFavourites(Set<Movie> favourites) {
        this.favourites = favourites;
    }
}