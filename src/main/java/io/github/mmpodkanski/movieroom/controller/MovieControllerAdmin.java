package io.github.mmpodkanski.movieroom.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.request.MovieWriteModel;
import io.github.mmpodkanski.movieroom.models.response.MovieReadModel;
import io.github.mmpodkanski.movieroom.service.CommentService;
import io.github.mmpodkanski.movieroom.service.MovieService;
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

    // only for admin
    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(@Valid @RequestBody MovieWriteModel newMovie) {
        // TODO: if principal != admin -> MovieToAccept !
        var result = service.createMovie(newMovie, true);
        logger.warn("Adding a new movie!");
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PatchMapping("/accept/{id}")
    ResponseEntity<?> acceptMovie(@PathVariable int id) {
        service.changeStatusOfMovie(id);
        return ResponseEntity.noContent().build();
    }

    //FIXME: not found
    @PatchMapping("/add/actor/{id}")
    ResponseEntity<Integer> addActor(
            @RequestBody @NotBlank TextNode name,
            @PathVariable int id
    ) {
        service.insertActorToMovie(Set.of(name.asText()), id);
        return ResponseEntity.noContent().build();
    }

    //FIXME: not found
    @PatchMapping("/add/actors/{id}")
    ResponseEntity<Integer> addActors(
            @RequestBody @NotBlank Set<String> names,
            @PathVariable int id
    ) {
        service.insertActorToMovie(names, id);
        return ResponseEntity.noContent().build();
    }

    //    @PutMapping("/movie/update/{id}")
//    ResponseEntity<?> updateMovie(
//            @RequestBody MovieWriteModel updatedMovie,
//            @PathVariable int id
//    ) {
//       service.updateMovie(updatedMovie, id);
//       return ResponseEntity.noContent().build();
//    }

    @GetMapping("/accept")
    ResponseEntity<List<MovieReadModel>> getAllMoviesToAccept() {
        logger.warn("Exposing all the movies to accept!");
        return ResponseEntity.ok(service.readAllMoviesToAccept());
    }

    // FIXME: spring doesn't know whose do (String or int)
    @DeleteMapping("/remove/{id}")
    ResponseEntity<Integer> removeMovieById(@PathVariable int id) {
        service.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }

    // FIXME: spring doesn't know whose do (String or int)
    @DeleteMapping("/remove/{title}")
    ResponseEntity<String> removeMovieByTitle(@PathVariable String title) {
        service.deleteMovieByTitle(title);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    ResponseEntity<MovieReadModel> deleteCommentFromMovie(
            @PathVariable int id,
            @PathVariable int commentId
    ) {
        commentService.removeComment(commentId);
        return ResponseEntity.ok().build();
    }


}
