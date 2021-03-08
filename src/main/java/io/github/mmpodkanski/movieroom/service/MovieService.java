package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.ECategory;
import io.github.mmpodkanski.movieroom.models.ERole;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.request.CommentRequest;
import io.github.mmpodkanski.movieroom.models.request.MovieRequest;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ActorService actorService;
    private final CommentService commentService;

    MovieService(
            final MovieRepository movieRepository,
            final UserService userService,
            final CategoryService categoryService,
            final ActorService actorService,
            final CommentService commentService
    ) {
        this.movieRepository = movieRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.actorService = actorService;
        this.commentService = commentService;
    }

     /*
        Read methods
    */

    public List<MovieResponse> readAllMovies() {
        return movieRepository.findAll()
                .stream()
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> readAllMoviesByYear(String year) {
        if (movieRepository.findAllByReleaseDate(year).isEmpty()) {
            throw new ApiBadRequestException("Movies made in that year not found");
        }
        return movieRepository.findAllByReleaseDate(year)
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

    public List<MovieResponse> readAllTopRatedMovies() {
        return movieRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Movie::getStars)
                        .reversed())
                .limit(5)
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> readAllTheNewestAddedMovies() {
        return movieRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Movie::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(5)
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public MovieResponse readMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
    }

    public MovieResponse readMovieByTitle(String movieTitle) {
        return movieRepository
                .findByTitle(
                        movieTitle.substring(0, 1).toUpperCase() + movieTitle.substring(1))
                .filter(Movie::isAcceptedByAdmin)
                .map(MovieResponse::new)
                .orElseThrow(() ->
                        new ApiNotFoundException("No movie exists with that title"));
    }

    public boolean checkIfUserAlreadyAddedFav(int movieId, int userId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        return userService.existsUserByFavourite(userId, movie);
    }


    /*
        Create methods
    */

    public Movie createMovie(
            MovieRequest newMovie,
            int userId
    ) {
        if (movieRepository.existsByTitle(newMovie.getTitle())) {
            throw new ApiBadRequestException("Movie with that title already exists!");
        }
        boolean createdByAdmin = userService
                .loadUserById(userId)
                .getRole()
                .equals(ERole.ROLE_ADMIN);

        var movie = mapMovieRequest(newMovie, createdByAdmin);
        return movieRepository.save(movie);
    }

    /*
        Update methods
    */

    public void updateMovie(MovieRequest updatedMovie, int movieId) {
        var movieToUpdate = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
        var newMovie = mapMovieRequest(updatedMovie, true);

        movieToUpdate.update(newMovie);
        movieRepository.save(movieToUpdate);
    }

    @Transactional
    public void changeStatusOfMovie(int id) {
        movieRepository.findById(id)
                .ifPresent(movie -> movie.setAcceptedByAdmin(true));
    }

    public void addCommentToMovie(CommentRequest newComment, int movieId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
        var comment = commentService.createComment(newComment, movie);

        movie.addComment(comment);
        movieRepository.save(movie);
    }

//    public void insertActorToMovie(
//            Set<String> newActors,
//            int id
//    ) {
//        var movie = movieRepository.findById(id).orElseThrow(() ->
//                new ApiBadRequestException("Movie with that id not exists!"));
//
//        Set<Actor> actors = actorService.checkActors(newActors);
//        movie.addActors(actors);
//        movieRepository.save(movie);

//    }


    @Transactional
    public void giveStar(int userId, int movieId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        var user = userService.loadUserById(userId);

        if (userService.existsByFavourites(movie)) {
            throw new ApiBadRequestException("Movie already exists in favourites!");
        }
        user.addFavourite(movie);
    }

    /*
        Delete methods
    */

    @Transactional
    public void deleteMovieById(int movieId) {
        movieRepository.deleteById(movieId);
    }

    public void deleteCommentFromMovie(int commentId) {
        var comment = commentService.findCommentById(commentId);
        var movie = comment.getMovie();

        movie.removeComment(comment);
        movieRepository.save(movie);

    }

    @Transactional
    public void deleteStar(int userId, int movieId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        var user = userService.loadUserById(userId);
        if (!userService.existsByFavourites(movie)) {
            throw new ApiBadRequestException("Movie not exists in favourites!");
        }
        user.removeFavourite(movie);
    }

    /*
        MAP (MODEL -> ENTITY)
    */

    private Movie mapMovieRequest(
            MovieRequest movieModel,
            boolean createdByAdmin
    ) {
        var createdAt = OffsetDateTime.now();
        ECategory category = categoryService.checkCategory(movieModel.getCategory());
        Set<Actor> actors = actorService.checkActors(movieModel.getActors());

        return movieModel.toMovie(createdAt, createdByAdmin, actors, category);
    }
}
