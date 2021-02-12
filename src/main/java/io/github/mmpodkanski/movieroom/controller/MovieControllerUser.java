package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.request.CommentRequest;
import io.github.mmpodkanski.movieroom.models.request.MovieRequest;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.service.CommentService;
import io.github.mmpodkanski.movieroom.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieControllerUser {
    private final Logger logger = LoggerFactory.getLogger(MovieControllerUser.class);
    private final MovieService movieService;
    private final CommentService commentService;

    MovieControllerUser(final MovieService movieService, final CommentService commentService) {
        this.movieService = movieService;
        this.commentService = commentService;
    }

    @PostMapping("/init")
    ResponseEntity<Movie> addMovieToAccept(@Valid @RequestBody MovieRequest newMovie) {
        logger.warn("Adding a new movie to accept!");
        var result = movieService.createMovie(newMovie, false);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments/add")
    ResponseEntity<MovieResponse> addCommentToMovie(
            @PathVariable("id") int movieId,
            @RequestBody CommentRequest comment
    ) {
        logger.info("Adding a new comment to movie(id): " + movieId);
        commentService.createComment(comment, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{id}")
    ResponseEntity<?> addToFavourites(@PathVariable("id") int movieId, @RequestBody int userId) {
        logger.info("User(id): " + userId + " added movie(id): " + movieId + "to favourites");
        movieService.giveStar(userId, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{id}")
    ResponseEntity<?> removeFromFavourites(@PathVariable("id") int movieId, @RequestBody int userId) {
        movieService.deleteStar(userId, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<MovieResponse>> getAllMovies() {
        logger.warn("Exposing all the movies!");
        var movieList = movieService.readAllMovies();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieResponse> getMovieById(@PathVariable int id) {
        var movie = movieService.readMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping(params = "title")
    ResponseEntity<MovieResponse> getMovieByTitle(@RequestParam String title) {
        var movie = movieService.readMovieByTittle(title);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieResponse>> getMoviesByYear(@RequestParam String year) {
        var movieList = movieService.readMoviesByYear(year);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }


}
