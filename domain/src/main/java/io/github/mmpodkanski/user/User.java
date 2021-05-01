package io.github.mmpodkanski.user;

import java.util.Set;

class User {
    static User restore(UserSnapshot snapshot) {
        return new User(
                snapshot.getId(),
                snapshot.getUsername(),
                snapshot.getEmail(),
                snapshot.getPassword(),
                snapshot.getRole(),
                snapshot.isLocked(),
                snapshot.getFavourites()
        );
    }

    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private ERole role;
    private boolean locked;
    private boolean enabled = true;
    private Set<UserMovie> favourites;

    public User(
            final int id,
            final String username,
            final String email,
            final String password,
            final ERole role,
            final boolean locked,
            final Set<UserMovie> favourites
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.favourites = favourites;
    }

    public UserSnapshot getSnapshot() {
        return new UserSnapshot(
                id,
                username,
                email,
                password,
                role,
                locked,
                enabled,
                favourites
        );
    }

    void addFavourite(UserMovie movie) {
        favourites.add(movie);
    }

    void removeFavourite(UserMovie movie) {
        favourites.remove(movie);
    }

    void setUserRole() {
        role = ERole.ROLE_USER;
    }

    void setAdminRole() {
        role = ERole.ROLE_ADMIN;
    }

    void toggleLocked() {
        locked = !locked;
    }

}