package io.github.mmpodkanski.auth;

import java.util.Set;

class UserSnapshot {
    private int id;
    private String username;
    private String email;
    private String password;
    private ERole role;
    private boolean locked;
    private boolean enabled = true;
    private Set<UserMovie> favourites;

    public UserSnapshot() {
    }

    public UserSnapshot(
            final int id,
            final String username,
            final String email,
            final String password,
            final ERole role,
            final boolean locked,
            final boolean enabled,
            final Set<UserMovie> favourites
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
        this.favourites = favourites;
    }

    int getId() {
        return id;
    }

    String getUsername() {
        return username;
    }

    String getEmail() {
        return email;
    }

    String getPassword() {
        return password;
    }

    ERole getRole() {
        return role;
    }

    boolean isLocked() {
        return locked;
    }

    boolean isEnabled() {
        return enabled;
    }

    Set<UserMovie> getFavourites() {
        return favourites;
    }
}
