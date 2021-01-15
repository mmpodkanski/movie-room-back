package io.github.mmpodkanski.filmroom.repository.jpa;

import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.repository.MovieRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaMovieRepository extends MovieRepository, JpaRepository<Movie, Integer> {


}
