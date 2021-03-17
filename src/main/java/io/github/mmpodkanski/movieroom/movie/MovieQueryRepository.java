package io.github.mmpodkanski.movieroom.movie;

import io.github.mmpodkanski.movieroom.movie.dto.MovieResponseDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface MovieQueryRepository extends Repository<Movie, Integer> {
    List<MovieResponseDto> findBy();

    List<MovieResponseDto> findMoviesByOrderByCreatedAtDesc();

    Optional<MovieResponseDto> findDtoById(int id);

    List<MovieResponseDto> findAllByReleaseDate(String year);

    Optional<MovieResponseDto> findByTitle(String title);

}
