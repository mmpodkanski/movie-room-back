package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.MovieResponseDto;

import java.util.List;
import java.util.Optional;

interface MovieQueryRepository {

    List<MovieResponseDto> findMoviesBy();

    List<MovieResponseDto> findFirst5ByOrderByStarsDesc();

    List<MovieResponseDto> findMoviesByReleaseDate(String year);

    List<MovieResponseDto> findMoviesByOrderByCreatedAtDesc();

    Optional<MovieResponseDto> findDtoById(int id);

    Optional<MovieResponseDto> findDtoByTitle(String title);

    int count();

}
