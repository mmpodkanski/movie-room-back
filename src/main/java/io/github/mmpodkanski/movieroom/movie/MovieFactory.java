package io.github.mmpodkanski.movieroom.movie;

import io.github.mmpodkanski.movieroom.category.ECategory;
import io.github.mmpodkanski.movieroom.movie.dto.MovieRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class MovieFactory {

    Movie from(MovieRequestDto source, LocalDateTime createdAt, boolean createdByAdmin, ECategory category) {
        var movie = new Movie();

        movie.setTitle(source.getTitle());
        movie.setDescription(source.getDescription());
        movie.setDirector(source.getDirector());
        movie.setProducer(source.getProducer());
        movie.setReleaseDate(source.getReleaseDate());
        movie.setCreatedAt(createdAt);
        movie.setAcceptedByAdmin(createdByAdmin);
        movie.setCategory(category);
        movie.setImgLogoUrl(source.getImgLogoUrl());
        movie.setImgBackUrl(source.getImgBackUrl());

        return movie;
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
