package io.github.mmpodkanski.filmroom.controller;

import io.github.mmpodkanski.filmroom.models.response.MovieReadModel;
import io.github.mmpodkanski.filmroom.models.request.MovieWriteModel;
import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService service;

    MovieController(final MovieService service) {
        this.service = service;
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

//    @PostMapping
//    ResponseEntity<MovieWriteModel> addClearMovie(
//            @Valid @RequestBody MovieWriteModel newMovie
//    ) {
//        logger.warn("Adding a new movie!");
//        var result = service.createClearMovie(newMovie);
//        // TODO: do smth with getId
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }

    // only for admin
    // TODO: principal
    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(
            @Valid @RequestBody MovieWriteModel newMovie
    ) {
        // TODO: if principal != admin -> MovieToAccept !
        logger.warn("Adding a new movie!");
        var result = service.createMovie(newMovie);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


    @PutMapping("/movie/update/{id}")
    ResponseEntity<?> upgradeMovie(
            @RequestBody MovieWriteModel updatedMovie,
            @PathVariable int id
    ) {
       service.updateMovie(updatedMovie, id);
       return ResponseEntity.noContent().build();
    }

    // only for admin
    //FIXME: not found
    @PatchMapping("/movie/add/actor/{id}")
    ResponseEntity<Integer> addActorById(
            @RequestBody @NotBlank String name,
            @PathVariable int id
    ) {
        service.insertActorToMovie(Set.of(name), id);
        return ResponseEntity.noContent().build();
    }

    // only for admin
    //FIXME: not found

    @PatchMapping("/movie/add/actors/{id}")
    ResponseEntity<Integer> addActorsById(
            @RequestBody @NotBlank Set<String> names,
            @PathVariable int id
    ) {
        service.insertActorToMovie(names, id);
        return ResponseEntity.noContent().build();
    }

    // TODO: add actor by - title - of movie
//    @PatchMapping("/movie/{title}/actor")
//    ResponseEntity<?> addActor(@RequestBody @NotBlank String name, @PathVariable String title) {
//        service.readMovieByTittle(title).
//        var movie = service.readMovieById(id);
//        service.insertActorToMovie(Set.of(name), movie);
//
//    }
//    return movie.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    // only for admin
    // FIXME: spring don't know whose do (String or int)
    @DeleteMapping("/remove/{id}")
    ResponseEntity<Integer> removeMovieById(@PathVariable int id) {
        service.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }

    // only for admin
    // FIXME: spring don't know whose do (String or int)
    @DeleteMapping("/remove/{title}")
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
