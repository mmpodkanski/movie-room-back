package io.github.mmpodkanski.movieroom.controller;

import com.fasterxml.jackson.databind.node.TextNode;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;
    private final CommentService commentService;

    MovieController(final MovieService movieService, final CommentService commentService) {
        this.movieService = movieService;
        this.commentService = commentService;
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

    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieRequest newMovie, int userId) {
        logger.warn("Adding a new movie!");
        var result = movieService.createMovie(newMovie, userId);
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

    //FIXME: not found
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/add/actor/{id}")
    public ResponseEntity<Integer> addActor(
            @RequestBody @NotBlank TextNode name,
            @PathVariable int id
    ) {
        logger.info("[ADMIN] Adding a new actor");
        movieService.insertActorToMovie(Set.of(name.asText()), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //FIXME: not found
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/add/actors/{id}")
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
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Integer> removeMovieById(@PathVariable int id) {
        logger.warn("[ADMIN] Removing movie with id: " + id);
        movieService.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    // FIXME: spring doesn't know whose do (String or int)
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
    public ResponseEntity<MovieResponse> deleteCommentFromMovie(
            @PathVariable("id") int movieId,
            @PathVariable int commentId
    ) {
        logger.warn("[ADMIN] Removing comment with id: " + commentId + "from movie(id): " + movieId);
        commentService.removeComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
