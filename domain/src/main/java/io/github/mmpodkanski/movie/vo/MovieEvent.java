package io.github.mmpodkanski.movie.vo;

import io.github.mmpodkanski.DomainEvent;
import io.github.mmpodkanski.movie.ECategory;

import java.time.Instant;
import java.util.Optional;

public class MovieEvent implements DomainEvent {
    public enum EState {
        ADDED, REMOVED
    }

    private final Instant occurredOn;
    private final UserSourceId id;
    private final EState state;
    private final Data data;

    public MovieEvent(final UserSourceId id, final EState state) {
        this(id, state, null);
    }

    public MovieEvent(final UserSourceId id, final EState state, final Data data) {
        this.id = id;
        this.state = state;
        this.data = data;
        this.occurredOn = Instant.now();
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public Optional<UserSourceId> getSourceId() {
        return Optional.ofNullable(id);
    }

    public EState getState() {
        return state;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private final int id;
        private final String title;
        private final String releaseDate;
        private final ECategory category;
        private final int stars;
        private final boolean acceptedByAdmin;
        private final String imgLogoUrl;

        public Data(final int id, final String title, final String releaseDate, final ECategory category, final int stars, final boolean acceptedByAdmin, final String imgLogoUrl) {
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

}
