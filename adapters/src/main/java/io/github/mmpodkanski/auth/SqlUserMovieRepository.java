package io.github.mmpodkanski.auth;

import org.springframework.data.repository.Repository;

interface SqlUserMovieRepository extends UserMovieRepository, Repository<UserMovie, Integer> {
}
