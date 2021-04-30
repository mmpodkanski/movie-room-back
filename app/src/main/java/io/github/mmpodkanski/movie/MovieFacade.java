package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.DomainEventPublisher;
import io.github.mmpodkanski.actor.ActorFacade;
import io.github.mmpodkanski.actor.dto.SimpleActor;
import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.movie.dto.MovieRequestDto;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import io.github.mmpodkanski.movie.vo.MovieEvent;
import io.github.mmpodkanski.movie.vo.UserSourceId;
import io.github.mmpodkanski.user.UserFacade;
import org.springframework.stereotype.Service;

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
    private final DomainEventPublisher publisher;

    MovieFacade(
            final MovieRepository movieRepository,
            final UserFacade userFacade,
            final CategoryFacade categoryFacade,
            final ActorFacade actorFacade,
            final CommentQueryRepository commentQueryRepository,
            final CommentFactory commentFactory,
            final CommentRepository commentRepository,
            final MovieFactory movieFactory,
            final DomainEventPublisher publisher
    ) {
        this.movieRepository = movieRepository;
        this.userFacade = userFacade;
        this.categoryFacade = categoryFacade;
        this.actorFacade = actorFacade;
        this.commentQueryRepository = commentQueryRepository;
        this.commentFactory = commentFactory;
        this.commentRepository = commentRepository;
        this.movieFactory = movieFactory;
        this.publisher = publisher;
    }

    boolean checkIfUserAlreadyAddedFav(int movieId, String username) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        return userFacade.existsByFavourites(username, toDto(movie));
    }

    public MovieResponseDto createMovie(
            MovieRequestDto newMovie,
            String username
    ) {
        if (movieRepository.existsByTitle(newMovie.getTitle())) {
            throw new ApiBadRequestException("Movie with that title already exists!");
        }
        boolean createdByAdmin = userFacade.checkIfAdmin(username);

        var actorsToSave = actorFacade.addSimpleActors(newMovie.getActors(), createdByAdmin);
        var movie = mapMovieRequest(newMovie, createdByAdmin, actorsToSave);

        return toDto(movieRepository.save(movie));
    }


    MovieResponseDto addCommentToMovie(CommentRequestDto commentReq, int movieId) {
        if (commentQueryRepository.existsByTitle(commentReq.getTitle())) {
            throw new ApiBadRequestException("Comment with that title already exists!");
        }

        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        String owner = userFacade.loadUserNameById(commentReq.getOwnerId());
        var comment = commentFactory.mapCommentDTO(commentReq, owner);

        movie.addComment(comment);
        return toDto(movieRepository.save(movie));
    }


    void updateMovie(
            MovieRequestDto requestMovie,
            int movieId,
            String username
    ) {
        var movieToUpdate = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        boolean createdByAdmin = userFacade.checkIfAdmin(username);
        var actorsToSave = actorFacade.addSimpleActors(requestMovie.getActors(), createdByAdmin);

        movieToUpdate.update(
                requestMovie.getTitle(),
                requestMovie.getDirector(),
                requestMovie.getProducer(),
                requestMovie.getDescription(),
                requestMovie.getReleaseDate(),
                ECategory.valueOf(requestMovie.getCategory()),
                actorsToSave,
                requestMovie.getImgLogoUrl(),
                requestMovie.getImgBackUrl()
        );
        movieRepository.save(movieToUpdate);
    }

//    @Transactional
    public void changeStatusOfMovie(int id) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        movie.toggleStatus();
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


    public void giveStar(String username, int movieId) {
        publisher.publish(toggleStar(username, movieId, MovieEvent.EState.ADDED));
    }

    public void removeStar(String username, int movieId) {
        publisher.publish(toggleStar(username, movieId, MovieEvent.EState.REMOVED));
    }

    private MovieEvent toggleStar(String username, int movieId, MovieEvent.EState state) {
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"))
                .getSnapshot();

        var userId = userFacade.loadIdByUsername(username);

        return new MovieEvent(
                new UserSourceId(String.valueOf(userId)),
                state,
                new MovieEvent.Data(
                        movieId,
                        movie.getTitle(),
                        movie.getReleaseDate(),
                        movie.getCategory(),
                        movie.getStars(),
                        movie.isAcceptedByAdmin(),
                        movie.getImgLogoUrl()
                )
        );
    }

    // FIXME: CANT DELETE PARENT
    public void deleteMovieById(int movieId) {
        var movieToDelete = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        movieRepository.delete(movieToDelete);
    }

    void deleteCommentFromMovie(int commentId) {

        commentRepository.deleteById(commentId);
    }


    private Movie mapMovieRequest(
            MovieRequestDto movieModel,
            boolean createdByAdmin,
            Set<SimpleActor> actorsToSave
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
                snap.getActors().stream().map(SimpleActor::restore).map(actorFacade::toSimpleDto).collect(Collectors.toList()),
                null,
                snap.isAcceptedByAdmin(),
                snap.getImgLogoUrl(),
                snap.getImgBackUrl()
        );
    }


}
