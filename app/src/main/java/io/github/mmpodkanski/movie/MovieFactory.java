package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.dto.SimpleActor;
import io.github.mmpodkanski.movie.dto.MovieRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class MovieFactory {

    Movie from(MovieRequestDto source, LocalDateTime createdAt, boolean createdByAdmin, Set<SimpleActor> actors) {
        return Movie.restore(new MovieSnapshot(
                source.getId(),
                source.getTitle(),
                source.getDirector(),
                source.getWriter(),
                source.getDescription(),
                source.getReleaseDate(),
                ECategory.valueOf(source.getCategory()),
                actors.stream().map(SimpleActor::getSnapshot).collect(Collectors.toSet()),
                null,
                0,
                createdAt,
                createdByAdmin,
                source.getImgLogoUrl(),
                source.getImgBackUrl()

        ));
    }
}
