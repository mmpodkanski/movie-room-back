package io.github.mmpodkanski.filmroom.controller;

import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.models.request.CommentDTO;
import io.github.mmpodkanski.filmroom.models.request.MovieWriteModel;
import io.github.mmpodkanski.filmroom.models.response.MovieReadModel;
import io.github.mmpodkanski.filmroom.service.CommentService;
import io.github.mmpodkanski.filmroom.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieControllerUser {
    private final Logger logger = LoggerFactory.getLogger(MovieControllerUser.class);
    private final MovieService service;
    private final CommentService commentService;

    MovieControllerUser(final MovieService service, final CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comments/add")
    ResponseEntity<MovieReadModel> addCommentToMovie(
            @PathVariable int id,
            @RequestBody CommentDTO comment
    ) {
        commentService.createComment(comment, id);
        return ResponseEntity.ok().build();
    }

    // only for admin
    @PostMapping("/init")
    ResponseEntity<Movie> addMovieToAccept(@Valid @RequestBody MovieWriteModel newMovie) {
        // TODO: if principal != admin -> MovieToAccept !
        var result = service.createMovie(newMovie, false);
        logger.warn("Adding a new movie to accept!");
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<MovieReadModel>> getAllMovies() {
        logger.warn("Exposing all the movies!");
        return ResponseEntity.ok(service.readAllMovies());
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieReadModel> getMovieById(@PathVariable int id) {
        return ResponseEntity.ok(service.readMovieById(id));
    }

    @GetMapping(params = "title")
    ResponseEntity<MovieReadModel> getMovieByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.readMovieByTittle(title));
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieReadModel>> getMoviesByYear(@RequestParam String year) {
        return ResponseEntity.ok(service.readMoviesByYear(year));

    }

}
