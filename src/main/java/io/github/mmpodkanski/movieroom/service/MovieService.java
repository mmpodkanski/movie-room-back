package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.ECategory;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.request.MovieRequest;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ActorService actorService;

    MovieService(
            final MovieRepository movieRepository,
            final UserService userService,
            final CategoryService categoryService,
            final ActorService actorService
    ) {
        this.movieRepository = movieRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.actorService = actorService;
    }

    public Movie createMovie(
            MovieRequest movieToSave,
            boolean createdByAdmin
    ) {
        if (movieRepository.existsByTitle(movieToSave.getTitle())) {
            throw new ApiBadRequestException("Movie with that title already exists!");
        }

        Movie newMovie = mapMovieRequest(movieToSave, createdByAdmin);
        return movieRepository.save(newMovie);
    }

    public void changeStatusOfMovie(int id) {
        movieRepository.findById(id)
                .ifPresent(movie -> movie.setAcceptedByAdmin(true));
    }

    public List<MovieResponse> readAllMovies() {
        return movieRepository.findAll()
                .stream()
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> readAllMoviesToAccept() {
        return movieRepository.findAll()
                .stream()
                .filter(movie -> !movie.isAcceptedByAdmin())
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public MovieResponse readMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
    }

    public MovieResponse readMovieByTittle(String movieTitle) {
        return movieRepository
                .findByTitle(
                        movieTitle.substring(0, 1).toUpperCase() + movieTitle.substring(1))
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .orElseThrow(() ->
                        new ApiNotFoundException("No movie exists with that title"));

    }

    public List<MovieResponse> readMoviesByYear(String year) {
        if (movieRepository.findAllByReleaseDate(year).isEmpty()) {
            throw new ApiBadRequestException("Movies made in that year not found");
        }
        return movieRepository.findAllByReleaseDate(year)
                .stream()
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public void insertActorToMovie(
            Set<String> newActors,
            int id
    ) {
        var movie = movieRepository.findById(id).orElseThrow(() ->
                new ApiBadRequestException("Movie with that id not exists!"));

        Set<Actor> actors = actorService.checkActors(newActors);
        movie.addActors(actors);
        movieRepository.save(movie);
    }

    public void deleteMovieById(int movieId) {
        movieRepository.deleteById(movieId);
    }

//    public void deleteMovieByTitle(String title) {
//        movieRepository.deleteByTitle(title);
//    }

    // STARS //
    // TODO: CLEAN CODE !!!!!!
    public void giveStar(int userId, int idMovie) {
        var movie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        var user = userService.loadUserById(userId);

        if (userService.existsByFavourites(movie)) {
            throw new ApiBadRequestException("Movie already exists in favourites!");
        }
        user.addFavourite(movie);
    }

    public void deleteStar(int userId, int idMovie) {
        var movie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        var user = userService.loadUserById(userId);

        if (userService.existsByFavourites(movie)) {
            throw new ApiBadRequestException("Movie already exists in favourites!");
        }
        user.removeFavourite(movie);
    }

    // MAP //
    public Movie mapMovieRequest(
            MovieRequest movieModel,
            boolean createdByAdmin
    ) {
        var createdAt = OffsetDateTime.now();
        ECategory category = categoryService.checkCategory(movieModel.getCategory());
        Set<Actor> actors = actorService.checkActors(movieModel.getActors());

        return movieModel.toMovie(createdAt, createdByAdmin, actors, category);
    }
}
