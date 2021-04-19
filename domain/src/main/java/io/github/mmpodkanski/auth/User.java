package io.github.mmpodkanski.auth;

class User {
    static User restore (UserSnapshot snapshot) {
        return new User(
                snapshot.getId(),
                snapshot.getUsername(),
                snapshot.getEmail(),
                snapshot.getPassword(),
                snapshot.getRole(),
                snapshot.isLocked()
        );
    }

    private int id;
    private String username;
    private String email;
    private String password;
    private ERole role;
    private boolean locked;
    private boolean enabled = true;
//    @OneToMany
//    private Set<MovieSnapshot> favourites = new HashSet<>();

    public User(
            final int id,
            final String username,
            final String email,
            final String password,
            final ERole role,
            final boolean locked
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    public UserSnapshot getSnapshot() {
        return new UserSnapshot(
                id,
                username,
                email,
                password,
                role,
                locked,
                enabled
        );
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

//    void addFavourite(MovieSnapshot movie) {
//        favourites.add(movie);
//    }
//
//    void removeFavourite(MovieSnapshot movie) {
////        if (favourites.isEmpty()) {
////            throw new ApiBadRequestException("User doesn't has favourites!");
////        }
//
//        favourites.remove(movie);
//    }

//    Set<MovieSnapshot> getFavourites() {
//        return favourites;
//    }
//
//    void setFavourites(Set<MovieSnapshot> favourites) {
//        this.favourites = favourites;
//    }
}