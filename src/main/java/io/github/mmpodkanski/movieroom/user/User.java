package io.github.mmpodkanski.movieroom.user;

import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Set;

@Entity
@EqualsAndHashCode
@Getter
@Setter
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
    @OneToMany
    private Set<MovieQueryDtoEntity> favourites;

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

    void addFavourite(MovieQueryDtoEntity movieQueryDtoEntity) {
        favourites.add(movieQueryDtoEntity);
    }

    void removeFavourite(MovieQueryDtoEntity movieQueryDtoEntity) {
        favourites.remove(movieQueryDtoEntity);
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
}