package io.github.mmpodkanski.user;

import org.springframework.data.repository.Repository;

interface SqlUserMovieRepository extends UserMovieRepository, Repository<UserMovie, Integer> {
}

interface SqlUserMovieQueryRepository extends UserMovieQueryRepository, Repository<UserMovie, Integer> {
}

