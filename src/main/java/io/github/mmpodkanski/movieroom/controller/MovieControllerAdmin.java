package io.github.mmpodkanski.movieroom.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import io.github.mmpodkanski.movieroom.models.Movie;
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
@RequestMapping("/movies/admin")
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieControllerAdmin {
    private final Logger logger = LoggerFactory.getLogger(MovieControllerUser.class);
    private final MovieService service;
    private final CommentService commentService;

    MovieControllerAdmin(MovieService service, CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
    }

    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieRequest newMovie) {
        logger.warn("Adding a new movie!");
        var result = service.createMovie(newMovie, true);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/accept")
    ResponseEntity<List<MovieResponse>> getAllMoviesToAccept() {
        logger.warn("Exposing all the movies to accept!");
        List<MovieResponse> movieList = service.readAllMoviesToAccept();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/accept/{id}")
    public ResponseEntity<?> acceptMovie(@PathVariable int id) {
        logger.info("Movie with id: " + id + " has been accepted");
        service.changeStatusOfMovie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //FIXME: not found
    @Transactional
    @PatchMapping("/add/actor/{id}")
    public ResponseEntity<Integer> addActor(
            @RequestBody @NotBlank TextNode name,
            @PathVariable int id
    ) {
        service.insertActorToMovie(Set.of(name.asText()), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //FIXME: not found
    @Transactional
    @PatchMapping("/add/actors/{id}")
    public ResponseEntity<Integer> addActors(
            @RequestBody @NotBlank Set<String> names,
            @PathVariable int id
    ) {
        service.insertActorToMovie(names, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Integer> removeMovieById(@PathVariable int id) {
        service.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    // FIXME: spring doesn't know whose do (String or int)
//    @Transactional
//    @DeleteMapping("/remove/{title}")
//    public ResponseEntity<String> removeMovieByTitle(@PathVariable String title) {
//        service.deleteMovieByTitle(title);
//        return ResponseEntity.noContent().build();
//    }

    @Transactional
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<MovieResponse> deleteCommentFromMovie(
            @PathVariable int id,
            @PathVariable int commentId
    ) {
        commentService.removeComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
