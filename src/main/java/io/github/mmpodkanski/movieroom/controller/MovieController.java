package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.request.CommentRequest;
import io.github.mmpodkanski.movieroom.models.request.MovieRequest;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.service.CommentService;
import io.github.mmpodkanski.movieroom.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;
    private final CommentService commentService;

    MovieController(
            final MovieService movieService,
            final CommentService commentService
    ) {
        this.movieService = movieService;
        this.commentService = commentService;
    }

    @GetMapping
    ResponseEntity<List<MovieResponse>> getAllMovies() {
        logger.warn("Exposing all the movies!");
        var movieList = movieService.readAllMovies();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieResponse>> getMoviesByYear(@RequestParam String year) {
        logger.info("Exposing a movie!");
        var movieList = movieService.readMoviesByYear(year);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieResponse> getMovieById(@PathVariable int id) {
        logger.info("Exposing a movie!");
        var movie = movieService.readMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping(params = "title")
    ResponseEntity<MovieResponse> getMovieByTitle(@RequestParam String title) {
        logger.info("Exposing a movie!");
        var movie = movieService.readMovieByTitle(title);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(
            @Valid @RequestBody MovieRequest newMovie,
            @AuthenticationPrincipal User user
    ) {
        logger.warn("Adding a new movie!");
        var result = movieService.createMovie(newMovie, user.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments/add")
    ResponseEntity<MovieResponse> addComment(
            @PathVariable("id") int movieId,
            @RequestBody CommentRequest comment
    ) {
        logger.info("Adding a new comment to movie(id): " + movieId);
        movieService.addCommentToMovie(comment, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/{id}", params = "add-fav")
    ResponseEntity<?> addFavourite(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        logger.info("User(id): " + user.getId() + " added movie(id): " + movieId + " to favourites");
        movieService.giveStar(user.getId(), movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/{id}", params = "remove-fav")
    ResponseEntity<?> removeFavourite(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        logger.info("User(id): " + user.getId() + " removed movie(id): " + movieId + " from favourites");
        movieService.deleteStar(user.getId(), movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/actors/add")
    public ResponseEntity<Integer> addActors(
            @RequestBody @NotBlank Set<String> names,
            @PathVariable int id
    ) {
        logger.info("[ADMIN] Adding new actors");
        movieService.insertActorToMovie(names, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}", params = "remove")
    public ResponseEntity<Integer> removeMovie(@PathVariable int id) {
        logger.warn("[ADMIN] Removing movie with id: " + id);
        movieService.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @Transactional
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @DeleteMapping("/remove/{title}")
//    public ResponseEntity<String> removeMovieByTitle(@PathVariable String title) {
//        movieService.deleteMovieByTitle(title);
//        return ResponseEntity.noContent().build();
//    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<MovieResponse> removeComment(
            @PathVariable("id") int movieId,
            @PathVariable int commentId
    ) {
        logger.warn("[ADMIN] Removing comment(id): " + commentId + " from movie(id): " + movieId);
        movieService.deleteCommentFromMovie(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
