package io.github.mmpodkanski.movie.dto;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.actor.dto.ActorSimpleResponseDto;
import io.github.mmpodkanski.movie.ECategory;

import java.util.List;

public interface MovieResponseDto {
    static MovieResponseDto create(
            final int id,
            final String title,
            final String description,
            final String director,
            final String writer,
            final ECategory category,
            final String releaseDate,
            final int stars,
            final List<ActorSimpleResponseDto> actors,
            final List<CommentResponseDto> comments,
            final boolean isAcceptedByAdmin,
            final String imgLogoUrl,
            final String imgBackUrl
    ) {
        return new DeserializationImpl(id, title, description, director, writer, category, releaseDate, stars, actors, comments, isAcceptedByAdmin, imgLogoUrl, imgBackUrl);
    }

    int getId();

    String getTitle();

    String getDescription();

    String getDirector();

    String getWriter();

    ECategory getCategory();

    String getReleaseDate();

    int getStars();

    List<ActorSimpleResponseDto> getActors();

    List<CommentResponseDto> getComments();

    boolean isAcceptedByAdmin();

    String getImgLogoUrl();

    String getImgBackUrl();


    class DeserializationImpl implements MovieResponseDto {
        private final int id;
        private final String title;
        private final String description;
        private final String director;
        private final String writer;
        private final ECategory category;
        private final String releaseDate;
        private final int stars;
        private final List<ActorSimpleResponseDto> actors;
        private final List<CommentResponseDto> comments;
        private final boolean acceptedByAdmin;
        private final String imgLogoUrl;
        private final String imgBackUrl;

        DeserializationImpl(
                final int id,
                final String title,
                final String description,
                final String director,
                final String writer,
                final ECategory category,
                final String releaseDate,
                final int stars,
                final List<ActorSimpleResponseDto> actors,
                final List<CommentResponseDto> comments,
                final boolean acceptedByAdmin,
                final String imgLogoUrl,
                final String imgBackUrl
        ) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.director = director;
            this.writer = writer;
            this.category = category;
            this.releaseDate = releaseDate;
            this.stars = stars;
            this.actors = actors;
            this.comments = comments;
            this.acceptedByAdmin = acceptedByAdmin;
            this.imgLogoUrl = imgLogoUrl;
            this.imgBackUrl = imgBackUrl;
        }


        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getDirector() {
            return director;
        }

        @Override
        public String getWriter() {
            return writer;
        }

        @Override
        public ECategory getCategory() {
            return category;
        }

        @Override
        public String getReleaseDate() {
            return releaseDate;
        }

        @Override
        public int getStars() {
            return stars;
        }

        @Override
        public List<ActorSimpleResponseDto> getActors() {
            return actors;
        }

        @Override
        public List<CommentResponseDto> getComments() {
            return comments;
        }

        @Override
        public boolean isAcceptedByAdmin() {
            return acceptedByAdmin;
        }

        @Override
        public String getImgLogoUrl() {
            return imgLogoUrl;
        }

        @Override
        public String getImgBackUrl() {
            return imgBackUrl;
        }
    }

}
