package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.Actor;
import io.github.mmpodkanski.actor.ActorFacade;
import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.movie.dto.MovieRequestDto;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import io.github.mmpodkanski.user.ERole;
import io.github.mmpodkanski.user.User;
import io.github.mmpodkanski.user.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieFacade {
    private final MovieRepository movieRepository;
    private final UserFacade userFacade;
    private final CategoryFacade categoryFacade;
    private final ActorFacade actorFacade;
    private final CommentQueryRepository commentQueryRepository;
    private final CommentFactory commentFactory;
    private final CommentRepository commentRepository;
    private final MovieFactory movieFactory;

    MovieFacade(
            final MovieRepository movieRepository,
            final UserFacade userFacade,
            final CategoryFacade categoryFacade,
            final ActorFacade actorFacade,
            final CommentQueryRepository commentQueryRepository,
            final CommentFactory commentFactory,
            final CommentRepository commentRepository,
            final MovieFactory movieFactory
    ) {
        this.movieRepository = movieRepository;
        this.userFacade = userFacade;
        this.categoryFacade = categoryFacade;
        this.actorFacade = actorFacade;
        this.commentQueryRepository = commentQueryRepository;
        this.commentFactory = commentFactory;
        this.commentRepository = commentRepository;
        this.movieFactory = movieFactory;
    }

//    boolean checkIfUserAlreadyAddedFav(int movieId, int userId) {
//        var movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
//
//        return userFacade.existsUserByFavourite(userId, movie);
//    }

    public MovieResponseDto createMovie(
            MovieRequestDto newMovie,
            int userId
    ) {
        if (movieRepository.existsByTitle(newMovie.getTitle())) {
            throw new ApiBadRequestException("Movie with that title already exists!");
        }
        boolean createdByAdmin = userFacade
                .loadUserById(userId)
                .getRole()
                .equals(ERole.ROLE_ADMIN);

        var actorsToSave = actorFacade.addSimpleActors(newMovie.getActors());
        var movie = mapMovieRequest(newMovie, createdByAdmin, actorsToSave);

        return toDto(movieRepository.save(movie));
    }


    MovieResponseDto addCommentToMovie(CommentRequestDto commentReq, int movieId) {
        if (commentQueryRepository.existsByTitle(commentReq.getTitle())) {
            throw new ApiBadRequestException("Comment with that title already exists!");
        }

        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        User owner = userFacade.loadUserById(commentReq.getOwnerId());
        var comment = commentFactory.mapCommentDTO(commentReq, owner);

        movie.addComment(comment);
        return toDto(movieRepository.save(movie));
    }


    void updateMovie(MovieRequestDto requestMovie, int movieId) {
        var movieToUpdate = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        actorFacade.deleteActorsFromExistingMovie(movieToUpdate.getSnapshot().getActors().stream().map(Actor::restore).collect(Collectors.toSet()));

        movieToUpdate.update(
                requestMovie.getTitle(),
                requestMovie.getDirector(),
                requestMovie.getProducer(),
                requestMovie.getDescription(),
                requestMovie.getReleaseDate(),
                ECategory.valueOf(requestMovie.getCategory()),
// FIXME: OPTION TO ADD/REMOVE ACTORS
//                requestMovie.getActors(),
                requestMovie.getImgLogoUrl(),
                requestMovie.getImgBackUrl()
        );
        movieRepository.save(movieToUpdate);
    }

    @Transactional
    public void changeStatusOfMovie(int id) {
        movieRepository.findById(id)
                .ifPresent(Movie::toggleStatus);
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


//    @Transactional
//    public void giveStar(int userId, int movieId) {
//        var movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
//
//        var user = userFacade.loadUserById(userId);
//
//        if (userFacade.existsByFavourites(movie)) {
//            throw new ApiBadRequestException("Movie already exists in favourites!");
//        }
//
//        userFacade.addFavouriteToUser(movie, user);
//        movie.addStar();
//    }

    // FIXME: CANT DELETE PARENT
    public void deleteMovieById(int movieId) {
        var movieToDelete = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        actorFacade.deleteActorsFromExistingMovie(
                movieToDelete
                        .getSnapshot()
                        .getActors()
                        .stream()
                        .map(Actor::restore)
                        .collect(Collectors.toSet())
        );

        movieRepository.delete(movieToDelete);
    }

    void deleteCommentFromMovie(int commentId) {
        commentRepository.deleteById(commentId);
    }

//    @Transactional
//    public void deleteStar(int userId, int movieId) {
//        var movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));
//
//        var user = userFacade.loadUserById(userId);
//
//        if (!userFacade.existsByFavourites(movie)) {
//            throw new ApiBadRequestException("Movie not exists in favourites!");
//        }
//
//        userFacade.removeFavouriteFromUser(movie, user);
//        movie.removeStar();
//    }

    private Movie mapMovieRequest(
            MovieRequestDto movieModel,
            boolean createdByAdmin,
            Set<Actor> actorsToSave
    ) {
        var createdAt = LocalDateTime.now();
        ECategory category = categoryFacade.checkCategory(movieModel.getCategory());

        return movieFactory.from(movieModel, createdAt, createdByAdmin, category, actorsToSave);
    }

    private MovieResponseDto toDto(Movie movie) {
        var snap = movie.getSnapshot();
        return MovieResponseDto.create(
                snap.getId(),
                snap.getTitle(),
                snap.getDescription(),
                snap.getDirector(),
                snap.getProducer(),
                snap.getCategory(),
                snap.getReleaseDate(),
                snap.getStars(),
                snap.getActors().stream().map(Actor::restore).map(actorFacade::toSimpleDto).collect(Collectors.toList()),
                null,
                snap.isAcceptedByAdmin(),
                snap.getImgLogoUrl(),
                snap.getImgBackUrl()
        );
    }


}
