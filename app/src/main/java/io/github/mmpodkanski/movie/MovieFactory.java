package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.dto.SimpleActor;
import io.github.mmpodkanski.movie.dto.MovieRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class MovieFactory {

    Movie from(MovieRequestDto source, LocalDateTime createdAt, boolean createdByAdmin, ECategory category, Set<SimpleActor> actors) {
        return Movie.restore(new MovieSnapshot(
                source.getId(),
                source.getTitle(),
                source.getDirector(),
                source.getWriter(),
                source.getDescription(),
                source.getReleaseDate(),
                category,
                actors.stream().map(SimpleActor::getSnapshot).collect(Collectors.toSet()),
                null,
                0,
                createdAt,
                createdByAdmin,
                source.getImgLogoUrl(),
                source.getImgBackUrl()

        ));
    }
//    MovieResponseDto toDto(Movie source) {
//        var actors = source.getActors()
//                .stream()
//                .map(actor -> new ActorSimpleDto(actor.getId(), actor.getFirstName(), actor.getLastName()))
//                .sorted(Comparator.comparing(ActorSimpleDto::getFirstName))
//                .collect(Collectors.toList());
//
//        var comments = source.getComments()
//                .stream()
//                .map(comment -> new CommentResponseDto(
//                        comment.getId(),
//                        comment.getCreatedAt(),
//                        comment.getTitle(),
//                        comment.getDescription()
//                ))
//                .sorted(Comparator.comparing(Comment::getCreatedAt))
//                .collect(Collectors.toList());
//
//        return MovieResponseDto.create(
//                source.getId(),
//                source.getTitle(),
//                source.getDescription(),
//                source.getDirector(),
//                source.getProducer(),
//                source.getCategory().name(),
//                source.getReleaseDate(),
//                actors,
//                comments,
//                source.isAcceptedByAdmin(),
//                source.getImgLogoUrl(),
//                source.getImgBackUrl()
//        );
//    }
}
