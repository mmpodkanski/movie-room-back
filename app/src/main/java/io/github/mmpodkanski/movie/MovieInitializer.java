package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.dto.SimpleActorSnapshot;

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
                    "Vikings",
                    "Director",
                    "Producer",
                    "Description",
                    "releaseDate",
                    ECategory.ACTION,
                    Set.of(
                            new SimpleActorSnapshot(1, "Adam", "Kowalski")
                    ),
                    Set.of(
                            new CommentSnapshot(1, LocalDateTime.now(), "title", "description", "mmpodkanski")
                    ),
                    0,
                    LocalDateTime.now(),
                    true,
                    "https://www.themoviedb.org/t/p/original/lVMpw7S7WH7gZQH2ub7gyYEaoUO.png",
                    "https://www.themoviedb.org/t/p/original/wPOmXBgjy4xrxxw5gyVFDfhx8Ii.jpg"
            )));


        }
    }
}
