package io.github.mmpodkanski.movieroom.movie;

import io.github.mmpodkanski.movieroom.comment.dto.CommentRequestDto;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import io.github.mmpodkanski.movieroom.movie.dto.MovieRequestDto;
import io.github.mmpodkanski.movieroom.movie.dto.MovieResponseDto;
import io.github.mmpodkanski.movieroom.user.User;
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

    MovieController(final MovieFacade movieFacade, MovieQueryRepository movieQueryRepository) {
        this.movieFacade = movieFacade;
        this.movieQueryRepository = movieQueryRepository;
    }

//    @GetMapping
//    ResponseEntity<List<MovieResponseDtoCopy>> getMovies() {
//        logger.info("Exposing all the movies!");
//        var movieList = movieService.readAllMovies();
//        return new ResponseEntity<>(movieList, HttpStatus.OK);
//    }
//
//    @GetMapping("/top-rated")
//    ResponseEntity<List<MovieResponseDtoCopy>> getTopRatedMovies() {
//        var movieList = movieService.readAllTopRatedMovies();
//        return new ResponseEntity<>(movieList, HttpStatus.OK);
//    }
//
//    @GetMapping("/new-added")
//    ResponseEntity<List<MovieResponseDtoCopy>> getTheNewestMovies() {
//        var movieList = movieService.readAllTheNewestAddedMovies();
//        return new ResponseEntity<>(movieList, HttpStatus.OK);
//    }
//
//    @GetMapping(params = "year")
//    ResponseEntity<List<MovieResponseDtoCopy>> getMoviesByYear(@RequestParam String year) {
//        var movieList = movieService.readAllMoviesByYear(year);
//        return new ResponseEntity<>(movieList, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    ResponseEntity<MovieResponseDtoCopy> getMovieById(@PathVariable int id) {
//        logger.info("Exposing a movie!");
//        var movie = movieService.readMovieById(id);
//        return new ResponseEntity<>(movie, HttpStatus.OK);
//    }
//
//    @GetMapping(params = "title")
//    ResponseEntity<MovieResponseDtoCopy> getMovieByTitle(@RequestParam String title) {
//        logger.info("Exposing a movie by title!");
//        var movie = movieService.readMovieByTitle(title);
//        return new ResponseEntity<>(movie, HttpStatus.OK);
//    }

    @GetMapping
    ResponseEntity<List<MovieResponseDto>> getMovies() {
        logger.info("Exposing all the movies!");
        var movieList = movieQueryRepository.findBy();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "new-added")
    ResponseEntity<List<MovieResponseDto>> getTheNewestMovies() {
        var movieList = movieQueryRepository.findMoviesByOrderByCreatedAtDesc();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieResponseDto> getMovieById(@PathVariable int id) {
        logger.info("Exposing a movie!");
        var movie = movieQueryRepository.findDtoById(id)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that not exists!"));
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }



    @GetMapping("/check-fav/{id}")
    ResponseEntity<Boolean> existsUserFavourite(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        var result = movieFacade.checkIfUserAlreadyAddedFav(movieId, user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/add")
    ResponseEntity<Movie> addMovie(
            @RequestBody @Valid MovieRequestDto movieRequestDto,
            @AuthenticationPrincipal User user
    ) {
        logger.warn("Adding a new movie!");
        var result = movieFacade.createMovie(movieRequestDto, user.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments/add")
    ResponseEntity<Void> addComment(
            @PathVariable("id") int movieId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        logger.info("Adding a new comment to movie(id): " + movieId);
        movieFacade.addCommentToMovie(commentRequestDto, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/add-fav")
    ResponseEntity<Void> addToFavourites(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        logger.info("User(id): " + user.getId() + " added movie(id): " + movieId + " to favourites");
        movieFacade.giveStar(user.getId(), movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/remove-fav")
    ResponseEntity<Void> removeFromFavourites(
            @PathVariable("id") int movieId,
            @AuthenticationPrincipal User user
    ) {
        logger.info("User(id): " + user.getId() + " removed movie(id): " + movieId + " from favourites");
        movieFacade.deleteStar(user.getId(), movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PatchMapping("/{id}/edit")
//    ResponseEntity<MovieRequestDto> updateMovie(
//            @PathVariable int id,
//            @RequestBody @Valid MovieRequestDto movieRequestDto
//    ) {
//        logger.warn("[ADMIN] Updating movie with id: " + id);
//        movieService.updateMovie(movieRequestDto, id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @DeleteMapping(value = "/{id}")
//    ResponseEntity<Void> removeMovie(@PathVariable int id) {
//        logger.warn("[ADMIN] Removing movie with id: " + id);
//        movieService.deleteMovieById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/comments/{commentId}")
    ResponseEntity<Void> removeComment(
            @PathVariable("id") int movieId,
            @PathVariable int commentId
    ) {
        logger.warn("[ADMIN] Removing comment(id): " + commentId + " from movie(id): " + movieId);
        movieFacade.deleteCommentFromMovie(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
