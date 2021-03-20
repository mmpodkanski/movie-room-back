package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.movie.dto.MovieRequestDto;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import io.github.mmpodkanski.user.User;
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
class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieFacade movieFacade;
    private final MovieQueryRepository movieQueryRepository;

    MovieController(
            final MovieFacade movieFacade,
            final MovieQueryRepository movieQueryRepository
    ) {
        this.movieFacade = movieFacade;
        this.movieQueryRepository = movieQueryRepository;
    }


//    // TODO: REMOVE IT!
//    @GetMapping("/check-fav/{id}")
//    ResponseEntity<Boolean> existsUserFavourite(
//            @PathVariable("id") int movieId,
//            @AuthenticationPrincipal User user
//    ) {
//        var result = movieFacade.checkIfUserAlreadyAddedFav(movieId, user.getId());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping
    ResponseEntity<List<MovieResponseDto>> getMovies() {
        logger.info("Exposing all the movies!");
        var movieList = movieQueryRepository.findMoviesBy();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "top-rated")
    ResponseEntity<List<MovieResponseDto>> getTopRatedMovies() {
        var movieList = movieQueryRepository.findFirst5ByOrderByStarsDesc();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "new-added")
    ResponseEntity<List<MovieResponseDto>> getTheNewestMovies() {
        var movieList = movieQueryRepository.findMoviesByOrderByCreatedAtDesc();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieResponseDto>> getMoviesByYear(@RequestParam String year) {
        var movieList =  movieQueryRepository.findMoviesByReleaseDate(year);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieResponseDto> getMovieById(@PathVariable int id) {
        logger.info("Exposing a movie!");
        var movie = movieQueryRepository.findDtoById(id)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that not exists!"));
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping(params = "title")
    ResponseEntity<MovieResponseDto> getMovieByTitle(@RequestParam String title) {
        logger.info("Exposing a movie by title!");
        var movie = movieQueryRepository.findDtoByTitle(title)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that title not exists!"));
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }


    @PostMapping
    ResponseEntity<Movie> addMovie(
            @RequestBody @Valid MovieRequestDto movieRequestDto,
            @AuthenticationPrincipal User user
    ) {
        logger.warn("Adding a new movie!");
        var result = movieFacade.createMovie(movieRequestDto, user.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    ResponseEntity<Void> addComment(
            @PathVariable("id") int movieId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        logger.info("Adding a new comment to movie(id): " + movieId);
        movieFacade.addCommentToMovie(commentRequestDto, movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping(value = "/{id}", params = "add-fav")
//    ResponseEntity<Void> addToFavourites(
//            @PathVariable("id") int movieId,
//            @AuthenticationPrincipal User user
//    ) {
//        logger.info("User(id): " + user.getId() + " added movie(id): " + movieId + " to favourites");
//        movieFacade.giveStar(user.getId(), movieId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping(value = "/{id}", params = "remove-fav")
//    ResponseEntity<Void> removeFromFavourites(
//            @PathVariable("id") int movieId,
//            @AuthenticationPrincipal User user
//    ) {
//        logger.info("User(id): " + user.getId() + " removed movie(id): " + movieId + " from favourites");
//        movieFacade.deleteStar(user.getId(), movieId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PatchMapping("/{id}")
    ResponseEntity<MovieRequestDto> updateMovie(
            @PathVariable int id,
            @RequestBody @Valid MovieRequestDto movieRequestDto
    ) {
        logger.warn("[ADMIN] Updating movie with id: " + id);
        movieFacade.updateMovie(movieRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> removeMovie(@PathVariable int id) {
        logger.warn("[ADMIN] Removing movie with id: " + id);
        movieFacade.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/comments/{commentId}")
    ResponseEntity<Void> removeComment(
            @PathVariable("id") int movieId,
            @PathVariable int commentId
    ) {
        logger.warn("[ADMIN] Removing comment(id): " + commentId + " from movie(id): " + movieId);
        movieFacade.deleteCommentFromMovie(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}