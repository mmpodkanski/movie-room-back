package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface MovieQueryRepository extends Repository<Movie, Integer> {

    List<MovieResponseDto> findMoviesBy();

    List<MovieResponseDto> findFirst5ByOrderByStarsDesc();

    List<MovieResponseDto> findMoviesByReleaseDate(String year);

    List<MovieResponseDto> findMoviesByOrderByCreatedAtDesc();

    Optional<MovieResponseDto> findDtoById(int id);

    Optional<MovieResponseDto> findDtoByTitle(String title);

}
