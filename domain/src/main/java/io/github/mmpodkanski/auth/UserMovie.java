package io.github.mmpodkanski.auth;

import io.github.mmpodkanski.movie.ECategory;

class UserMovie {
//    static UserMovie createFrom(final MovieEvent source) {
//        return new UserMovie(
//                0,
//                source.getDescription(),
//                false,
//                source.getDeadline(),
//                0,
//                null,
//                source.getId()
//        );
//    }

    private int id;
    private String title;
    private String releaseDate;
    private ECategory category;
    private int stars;
    private boolean acceptedByAdmin;
    private String imgLogoUrl;

    protected UserMovie() {
    }

    UserMovie(int id, String title, String releaseDate, ECategory category, int stars, boolean acceptedByAdmin, String imgLogoUrl) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.category = category;
        this.stars = stars;
        this.acceptedByAdmin = acceptedByAdmin;
        this.imgLogoUrl = imgLogoUrl;
    }

    int getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    ECategory getCategory() {
        return category;
    }

    int getStars() {
        return stars;
    }

    boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }

    String getImgLogoUrl() {
        return imgLogoUrl;
    }

}
