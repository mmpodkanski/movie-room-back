package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.dto.SimpleActor;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;

import java.util.List;
import java.util.Optional;

public interface MovieQueryRepository {

    boolean existsByTitle(String title);

    boolean existsById(int id);

    List<MovieResponseDto> findMoviesByAcceptedByAdminTrue();

    List<MovieResponseDto> findMoviesByAcceptedByAdminFalse();

    List<MovieResponseDto> findFirst4ByOrderByStarsDesc();

    List<MovieResponseDto> findMoviesByReleaseDate(String year);

    List<MovieResponseDto> findFirst10ByOrderByCreatedAtDesc();

    Optional<MovieResponseDto> findDtoById(int id);

    Optional<MovieResponseDto> findDtoByTitle(String title);

    int count();

    List<MovieResponseDto> findAllMoviesByActorsContains(SimpleActor actor);
}
