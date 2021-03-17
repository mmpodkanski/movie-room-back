package io.github.mmpodkanski.movieroom.movie;

import io.github.mmpodkanski.movieroom.actor.ActorFacade;
import io.github.mmpodkanski.movieroom.category.CategoryFacade;
import io.github.mmpodkanski.movieroom.category.ECategory;
import io.github.mmpodkanski.movieroom.comment.CommentFacade;
import io.github.mmpodkanski.movieroom.comment.dto.CommentRequestDto;
import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import io.github.mmpodkanski.movieroom.movie.dto.MovieRequestDto;
import io.github.mmpodkanski.movieroom.user.ERole;
import io.github.mmpodkanski.movieroom.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MovieFacade {
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final CategoryFacade categoryFacade;
    private final ActorFacade actorFacade;
    private final CommentFacade commentFacade;
    private final MovieFactory movieFactory;

    MovieFacade(
            final MovieRepository movieRepository,
            final UserService userService,
            final CategoryFacade categoryFacade,
            final ActorFacade actorFacade,
            final CommentFacade commentFacade,
            final MovieFactory movieFactory
    ) {
        this.movieRepository = movieRepository;
        this.userService = userService;
        this.categoryFacade = categoryFacade;
        this.actorFacade = actorFacade;
        this.commentFacade = commentFacade;
        this.movieFactory = movieFactory;
    }

     /*
        Read methods
    */

//    List<MovieResponseDtoCopy> readAllMovies() {
//        return movieRepository.findAll()
//                .stream()
//                .filter(Movie::isAcceptedByAdmin)
//                .map(MovieResponseDtoCopy::new)
//                .collect(Collectors.toList());
//    }
//
//    List<MovieResponseDtoCopy> readAllMoviesByYear(String year) {
//        if (movieRepository.findAllByReleaseDate(year).isEmpty()) {
//            throw new ApiBadRequestException("Movies made in that year not found");
//        }
//        return movieRepository.findAllByReleaseDate(year)
//                .stream()
//                .filter(Movie::isAcceptedByAdmin)
//                .map(MovieResponseDtoCopy::new)
//                .collect(Collectors.toList());
//    }
//
//    public List<MovieResponseDtoCopy> readAllMoviesToAccept() {
//        return movieRepository.findAll()
//                .stream()
//                .filter(movie -> !movie.isAcceptedByAdmin())
//                .map(MovieResponseDtoCopy::new)
//                .collect(Collectors.toList());
//    }
//
//    List<MovieResponseDtoCopy> readAllTopRatedMovies() {
//        return movieRepository.findAll()
//                .stream()
//                .sorted(Comparator.comparingInt(Movie::getStars)
//                        .reversed())
//                .limit(5)
//                .map(MovieResponseDtoCopy::new)
//                .collect(Collectors.toList());
//    }
//
//    List<MovieResponseDtoCopy> readAllTheNewestAddedMovies() {
//        return movieRepository.findAll()
//                .stream()
//                .sorted(Comparator.comparing(Movie::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
//                .limit(5)
//                .map(MovieResponseDtoCopy::new)
//                .collect(Collectors.toList());
//    }
//
//    MovieResponseDtoCopy readMovieById(int movieId) {
//        return movieRepository.findById(movieId)
//                .filter(Movie::isAcceptedByAdmin)
//                .map(MovieResponseDtoCopy::new)
//                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
//    }
//
//    MovieResponseDtoCopy readMovieByTitle(String movieTitle) {
//        return movieRepository
//                .findByTitle(
//                        movieTitle.substring(0, 1).toUpperCase() + movieTitle.substring(1))
//                .filter(Movie::isAcceptedByAdmin)
//                .map(MovieResponseDtoCopy::new)
//                .orElseThrow(() ->
//                        new ApiNotFoundException("No movie exists with that title"));
//    }

    boolean checkIfUserAlreadyAddedFav(int movieId, int userId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        return userService.existsUserByFavourite(userId, new MovieQueryDtoEntity(movie.getId(), movie.getTitle()));
    }


    /*
        Create methods
    */

    public Movie createMovie(
            MovieRequestDto newMovie,
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
        var actorsToSave = actorFacade.returnActorsOrCreateNew(newMovie.getActors());

        movie.addActors(actorsToSave);
        return movieRepository.save(movie);
    }

    /*
        Update methods
    */

    void updateMovie(MovieRequestDto updatedMovie, int movieId) {
        var movieToUpdate = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        actorFacade.deleteActorsFromExistingMovie(movieToUpdate.getActors());

        movieToUpdate.update(mapMovieRequest(updatedMovie, true));
        movieRepository.save(movieToUpdate);
    }

    @Transactional
    public void changeStatusOfMovie(int id) {
        movieRepository.findById(id)
                .ifPresent(movie -> movie.setAcceptedByAdmin(true));
    }

    void addCommentToMovie(CommentRequestDto newComment, int movieId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
        var comment = commentFacade.createComment(newComment, new MovieQueryDtoEntity(movie.getId(), movie.getTitle()));

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
        if (userService.existsByFavourites(new MovieQueryDtoEntity(movieId, movie.getTitle()))) {
            throw new ApiBadRequestException("Movie already exists in favourites!");
        }

        userService.addFavouriteToUser(new MovieQueryDtoEntity(movieId, movie.getTitle()), user);
        movie.addStar();
    }

    /*
        Delete methods
    */

//    @Transactional
//    public void deleteMovieById(int movieId) {
//        var movieToDelete = movieRepository.findById(movieId)
//                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
//
//        actorService.deleteActorsFromExistingMovie(movieToDelete.getActors());
//        movieRepository.delete(movieToDelete);
//    }

    void deleteCommentFromMovie(int commentId) {
        var comment = commentFacade.findCommentById(commentId);
        var movie = movieRepository.findById(comment.getMovie().getId())
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        movie.removeComment(comment);
        movieRepository.save(movie);

    }

    @Transactional
    public void deleteStar(int userId, int movieId) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        var user = userService.loadUserById(userId);
        if (!userService.existsByFavourites(new MovieQueryDtoEntity(movieId, movie.getTitle()))) {
            throw new ApiBadRequestException("Movie not exists in favourites!");
        }

        userService.removeFavouriteFromUser(new MovieQueryDtoEntity(movieId, movie.getTitle()), user);
        movie.removeStar();
    }

    /*
        MAP (MODEL -> ENTITY)
    */


    private Movie mapMovieRequest(
            MovieRequestDto movieModel,
            boolean createdByAdmin
    ) {
        var createdAt = LocalDateTime.now();
        ECategory category = categoryFacade.checkCategory(movieModel.getCategory());
        return movieFactory.from(movieModel, createdAt, createdByAdmin, category);
    }
}
