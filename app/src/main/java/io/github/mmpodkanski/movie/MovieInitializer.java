package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.ActorSnapshot;

import java.time.LocalDateTime;
import java.util.Set;

class MovieInitializer {
    private final MovieRepository movieRepository;
    private final MovieQueryRepository movieQueryRepository;

    MovieInitializer(final MovieRepository movieRepository, final MovieQueryRepository movieQueryRepository) {
        this.movieRepository = movieRepository;
        this.movieQueryRepository = movieQueryRepository;
    }

    void init() {
        if (movieQueryRepository.count() == 0) {
//            Set<MovieSnapshot> EmptySet = Collections.emptySet();

            movieRepository.save(Movie.restore(new MovieSnapshot(
                    1,
                    "First",
                    "Director",
                    "Producer",
                    "Description",
                    "releaseDate",
                    ECategory.ACTION,
                    Set.of(
                            new ActorSnapshot(1, "Adam", "Kowalski", "2001", null, true)
                    ),
                    Set.of(
                            new CommentSnapshot(1, null, "title", "description", "mmpodkanski")
                    ),
                    0,
                    LocalDateTime.now(),
                    true,
                    null,
                    null
            )));


        }
    }
}