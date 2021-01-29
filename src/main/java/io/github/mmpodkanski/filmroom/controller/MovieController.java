package io.github.mmpodkanski.filmroom.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.models.request.CommentDTO;
import io.github.mmpodkanski.filmroom.models.request.MovieWriteModel;
import io.github.mmpodkanski.filmroom.models.response.MovieReadModel;
import io.github.mmpodkanski.filmroom.service.CommentService;
import io.github.mmpodkanski.filmroom.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService service;
    private final CommentService commentService;

    MovieController(final MovieService service, final CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
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

    @PostMapping("/{id}/comments/add")
    ResponseEntity<MovieReadModel> addCommentToMovie(
            @PathVariable int id,
            @RequestBody CommentDTO comment
    ) {
        commentService.createComment(comment, id);
        return ResponseEntity.ok().build();
    }

    // FIXME: only owner of comment can delete !!!
    @DeleteMapping("/{id}/comments/{commentId}")
    ResponseEntity<MovieReadModel> deleteCommentFromMovie(
            @PathVariable int id,
            @PathVariable int commentId
    ) {
        commentService.removeComment(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "title")
    ResponseEntity<MovieReadModel> getMovieByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.readMovieByTittle(title));
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieReadModel>> getMoviesByYear(@RequestParam String year) {
        return ResponseEntity.ok(service.readMoviesByYear(year));

    }

    // only for admin
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieWriteModel newMovie) {
        // TODO: if principal != admin -> MovieToAccept !
        logger.warn("Adding a new movie!");
        var result = service.createMovie(newMovie);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


//    @PutMapping("/movie/update/{id}")
//    ResponseEntity<?> updateMovie(
//            @RequestBody MovieWriteModel updatedMovie,
//            @PathVariable int id
//    ) {
//       service.updateMovie(updatedMovie, id);
//       return ResponseEntity.noContent().build();
//    }

    //FIXME: not found
    @PatchMapping("/add/actor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Integer> addActorById(
            @RequestBody @NotBlank TextNode name,
            @PathVariable int id
    ) {
        service.insertActorToMovie(Set.of(name.asText()), id);
        return ResponseEntity.noContent().build();
    }


    //FIXME: not found
    @PatchMapping("/add/actors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Integer> addActorsById(
            @RequestBody @NotBlank Set<String> names,
            @PathVariable int id
    ) {
        service.insertActorToMovie(names, id);
        return ResponseEntity.noContent().build();
    }


    // FIXME: spring don't know whose do (String or int)
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Integer> removeMovieById(@PathVariable int id) {
        service.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }

    // FIXME: spring don't know whose do (String or int)
    @DeleteMapping("/remove/{title}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> removeMovieByTitle(@PathVariable String title) {
        service.deleteMovieByTitle(title);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
