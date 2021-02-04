package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiRequestException;
import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.ECategory;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.request.MovieWriteModel;
import io.github.mmpodkanski.movieroom.models.response.MovieReadModel;
import io.github.mmpodkanski.movieroom.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final CategoryService categoryService;
    private final ActorService actorService;

    MovieService(final MovieRepository repository, final CategoryService categoryService, final ActorService actorService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.actorService = actorService;
    }

    public Movie createMovie(
            MovieWriteModel movieToSave,
            boolean createdByAdmin
    ) {
        if (repository.existsByTitle(movieToSave.getTitle())) {
            throw new ApiRequestException("Movie with that title already exists!");
        }
        var createdAt = OffsetDateTime.now();
        Movie newMovie = movieToSave.toMovie(createdAt);

        ECategory categories = categoryService.checkCategory(movieToSave.getCategory());
        Set<Actor> actors = actorService.checkActors(movieToSave.getActors(), newMovie);

        newMovie.setCategory(categories);
        newMovie.setActors(actors);
        newMovie.setAcceptedByAdmin(createdByAdmin);

        return repository.save(newMovie);
    }

    public void changeStatusOfMovie(int id) {
        repository.findById(id)
                .ifPresent(movie -> movie.setAcceptedByAdmin(true));
    }

    public List<MovieReadModel> readAllMovies() {
        return repository.findAll()
                .stream()
                .filter(Movie::getAcceptedByAdmin)
                .map(MovieReadModel::new)
                .collect(Collectors.toList());
    }

    public List<MovieReadModel> readAllMoviesToAccept() {
        return repository.findAll()
                .stream()
                .filter(movie -> !movie.getAcceptedByAdmin())
                .map(MovieReadModel::new)
                .collect(Collectors.toList());
    }

    public MovieReadModel readMovieById(int movieId) {
        return repository.findById(movieId)
                .filter(Movie::getAcceptedByAdmin)
                .map(MovieReadModel::new)
                .orElseThrow(() -> new ApiRequestException("Movie with that id not exists!"));
    }

    public MovieReadModel readMovieByTittle(String movieTitle) {
        return repository
                        .findByTitle(
                                movieTitle.substring(0, 1).toUpperCase() + movieTitle.substring(1))
                        .filter(Movie::getAcceptedByAdmin)
                        .map(MovieReadModel::new)
                        .orElseThrow(() ->
                                new ApiRequestException("No movie exists with that title"));

    }

    public List<MovieReadModel> readMoviesByYear(String year) {
        if (repository.findAllByReleaseDate(year).isEmpty()) {
            throw new ApiRequestException("Movies made in that year not found");
        }
        return repository.findAllByReleaseDate(year)
                .stream()
                .filter(Movie::getAcceptedByAdmin)
                .map(MovieReadModel::new)
                .collect(Collectors.toList());
    }



    public void insertActorToMovie(
            Set<String> newActors,
            int id
    ) {
        var movie = repository.findById(id).orElseThrow(() ->
                new ApiRequestException("Movie with that id not exists!"));

        Set<Actor> actors = actorService.checkActors(newActors, movie);
        actors.forEach(actor -> movie.getActors().add(actor));
        repository.save(movie);
    }

    public void deleteMovieById(int movieId) {
        repository.deleteById(movieId);
    }

    public void deleteMovieByTitle(String title) {
        repository.deleteByTitle(title);
    }

//    public void updateMovie(
//            final MovieWriteModel updatedMovie,
//            final int id
//    ) {
//        // FIXME: maybe no message from exception
//        Movie movieToUpdate = repository
//                .findById(id)
//                .orElseThrow(IllegalArgumentException::new);
//
//        Category categories = categoryService.returnExistsOrCreateNew(updatedMovie.getCategory(), movieToUpdate);
//        Set<Actor> actors = actorService.checkActors(updatedMovie.getActors(), movieToUpdate);
//        // TODO: awards
//
//        movieToUpdate.setDescription(movieToUpdate.getDescription());
//        movieToUpdate.setProducer(movieToUpdate.getProducer());
//        movieToUpdate.setDirector(movieToUpdate.getDirector());
//        movieToUpdate.setTitle(movieToUpdate.getTitle());
//        movieToUpdate.setCategory(category);
//        movieToUpdate.setActors(actors);
//        repository.save(movieToUpdate);
//
//    }
}
