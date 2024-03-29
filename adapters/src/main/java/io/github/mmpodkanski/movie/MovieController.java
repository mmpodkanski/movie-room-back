package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.movie.dto.MovieRequestDto;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

@RestController
@RequestMapping("/movies")
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


    @GetMapping("/check-fav/{id}")
    ResponseEntity<Boolean> existsUserFavourite(
            @PathVariable("id") int movieId
    ) {
        var currentUser = getAuth();

        var result = movieFacade.checkIfUserAlreadyAddedFav(movieId, currentUser.getName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<MovieResponseDto>> getMovies() {
        var movieList = movieQueryRepository.findMoviesByAcceptedByAdminTrue();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/file")
    void downloadMovies(HttpServletResponse response) throws IOException {
        var movieList = movieQueryRepository.findMoviesByAcceptedByAdminTrue();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"movies.csv\"");

        var writer = new CSVWriter(response.getWriter(), ';', '"', '\\', "\n");
        writer.writeNext(new String[] { "Title", "Category", "ReleaseDate"}, false);
        for (var movie : movieList) {
            writer.writeNext(new String[] { movie.getTitle(), movie.getCategory().toString(), movie.getReleaseDate() }, false);
        }
        writer.flush();
    }

    @GetMapping(params = "top-rated")
    ResponseEntity<List<MovieResponseDto>> getTopRatedMovies() {
        var movieList = movieQueryRepository.findFirst4ByOrderByStarsDesc()
                .stream()
                .filter(MovieResponseDto::isAcceptedByAdmin)
                .collect(Collectors.toList());

        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "new-added")
    ResponseEntity<List<MovieResponseDto>> getTheNewestMovies() {
        var movieList = movieQueryRepository.findFirst10ByOrderByCreatedAtDesc()
                .stream()
                .filter(MovieResponseDto::isAcceptedByAdmin)
                .collect(Collectors.toList());

        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping(params = "year")
    ResponseEntity<List<MovieResponseDto>> getMoviesByYear(@RequestParam String year) {
        var movieList = movieQueryRepository.findMoviesByReleaseDate(year)
                .stream()
                .filter(MovieResponseDto::isAcceptedByAdmin)
                .collect(Collectors.toList());

        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieResponseDto> getMovieById(@PathVariable int id) {
        logger.info("Exposing a movie!");
        var movie = movieQueryRepository.findDtoById(id)
                .filter(MovieResponseDto::isAcceptedByAdmin)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that not exists!"));

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping(params = "title")
    ResponseEntity<MovieResponseDto> getMovieByTitle(@RequestParam String title) {
        logger.info("Exposing a movie by title!");
        var movie = movieQueryRepository.findDtoByTitle(title)
                .filter(MovieResponseDto::isAcceptedByAdmin)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that title not exists!"));

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }


    @PostMapping
    ResponseEntity<MovieResponseDto> addMovie(
            @RequestBody MovieRequestDto movieRequestDto
    ) {
        logger.warn("Adding a new movie!");
        var currentUser = getAuth();
        var result = movieFacade.createMovie(movieRequestDto, currentUser.getName());

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    ResponseEntity<MovieResponseDto> addComment(
            @PathVariable("id") int movieId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        logger.info("Adding a new comment to movie(id): " + movieId);
        var result = movieFacade.addCommentToMovie(commentRequestDto, movieId);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}", params = "add-fav")
    ResponseEntity<Void> addToFavourites(
            @PathVariable("id") int movieId
    ) {
        var currentUser = getAuth();

        logger.info("User(name): " + currentUser.getName() + " adding movie(id): " + movieId + " to favourites");
        movieFacade.giveStar(currentUser.getName(), movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}", params = "remove-fav")
    ResponseEntity<Void> removeFromFavourites(
            @PathVariable("id") int movieId
    ) {
        var currentUser = getAuth();

        logger.info("User(id): " + currentUser.getName() + " removing movie(id): " + movieId + " from favourites");
        movieFacade.removeStar(currentUser.getName(), movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<MovieResponseDto> updateMovie(
            @PathVariable int id,
            @RequestBody @Valid MovieRequestDto movieRequestDto
    ) {
        logger.warn("[ADMIN] Updating movie with id: " + id);
        movieFacade.updateMovie(movieRequestDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeMovie(@PathVariable int id) {
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


    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
