package io.github.mmpodkanski.user.dto;

import io.github.mmpodkanski.movie.ECategory;

public class UserMovieDto {
    private final int id;
    private final String title;
    private final String releaseDate;
    private final ECategory category;
    private final int stars;
    private final boolean acceptedByAdmin;
    private final String imgLogoUrl;

    public UserMovieDto(int id, String title, String releaseDate, ECategory category, int stars, boolean acceptedByAdmin, String imgLogoUrl) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.category = category;
        this.stars = stars;
        this.acceptedByAdmin = acceptedByAdmin;
        this.imgLogoUrl = imgLogoUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ECategory getCategory() {
        return category;
    }

    public int getStars() {
        return stars;
    }

    public boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }
}
