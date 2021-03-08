package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.request.CommentRequest;
import io.github.mmpodkanski.movieroom.models.request.MovieRequest;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
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
import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    MovieController(
            final MovieService movieService
    ) {
        this.movieService = movieService;
    }

    @GetMapping
    ResponseEntity<List<MovieResponse>> getMovies() {
        logger.info("Exposing all the movies!");
        var movieList = movieService.readAllMovies();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/top-rated")
    ResponseEntity<List<MovieResponse>> getTopRatedMovies() {
        var movieList = movieService.readAllTopRatedMovies();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/new-added")
    ResponseEntity<List<MovieResponse>> getTheNewestMovies() {
        var movieList = movieService.readAllTheNewestAddedMovies();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieResponse>> getMoviesByYear(@RequestParam String year) {
        var movieList = movieService.readAllMoviesByYear(year);
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
        logger.info("Exposing a movie by title!");
        var movie = movieService.readMovieByTitle(title);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping("/check-fav/{id}")
    ResponseEntity<Boolean> existsUserFavourite(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        var result = movieService.checkIfUserAlreadyAddedFav(movieId, user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(
            @RequestBody @Valid MovieRequest movieRequest,
            @AuthenticationPrincipal User user
    ) {
        logger.warn("Adding a new movie!");
        var result = movieService.createMovie(movieRequest, user.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments/add")
    ResponseEntity<MovieResponse> addComment(
            @PathVariable("id") int movieId,
            @RequestBody @Valid CommentRequest commentRequest
    ) {
        logger.info("Adding a new comment to movie(id): " + movieId);
        movieService.addCommentToMovie(commentRequest, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/add-fav")
    ResponseEntity<?> addToFavourites(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        logger.info("User(id): " + user.getId() + " added movie(id): " + movieId + " to favourites");
        movieService.giveStar(user.getId(), movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/remove-fav")
    ResponseEntity<?> removeFromFavourites(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        logger.info("User(id): " + user.getId() + " removed movie(id): " + movieId + " from favourites");
        movieService.deleteStar(user.getId(), movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<MovieRequest> updateMovie(
            @PathVariable int id,
            @RequestBody @Valid MovieRequest movieRequest
    ) {
        logger.warn("[ADMIN] Updating movie with id: " + id);
        movieService.updateMovie(movieRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PatchMapping("/{id}/actors/add")
//    public ResponseEntity<Integer> addActors(
//            @RequestBody @NotBlank Set<String> names,
//            @PathVariable int id
//    ) {
//        logger.info("[ADMIN] Adding new actors");
//        movieService.insertActorToMovie(names, id);
//        return new ResponseEntity<>(HttpStatus.OK);

//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}", params = "remove")
    public ResponseEntity<Integer> removeMovie(@PathVariable int id) {
        logger.warn("[ADMIN] Removing movie with id: " + id);
        movieService.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


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
