package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.Actor;
import io.github.mmpodkanski.filmroom.models.Category;
import io.github.mmpodkanski.filmroom.models.ECategory;
import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.repository.MovieRepository;
import io.github.mmpodkanski.filmroom.models.request.MovieWriteModel;
import io.github.mmpodkanski.filmroom.models.response.MovieReadModel;
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

    public List<MovieReadModel> readAllMovies() {
        return repository.findAll()
                .stream()
                .map(MovieReadModel::new)
                .collect(Collectors.toList());
    }

    public MovieReadModel readMovieById(int movieId) {
        return repository.findById(movieId)
                .map(MovieReadModel::new)
                .orElseThrow(() -> new IllegalArgumentException("No movie exists with that id"));
    }

    public MovieReadModel readMovieByTittle(String movieTitle) {
        return repository
                .findByTitle(movieTitle.substring(0, 1).toUpperCase() + movieTitle.substring(1))
                .map(MovieReadModel::new)
                .orElseThrow(() -> new IllegalArgumentException("No movie exists with that title"));

    }

    public List<MovieReadModel> readMoviesByYear(String year) {
        if (repository.findAllByReleaseDate(year).isEmpty()) {
            throw new IllegalArgumentException("Movies made in that year not found");
        }
        return repository.findAllByReleaseDate(year)
                .stream()
                .map(MovieReadModel::new)
                .collect(Collectors.toList());
    }

    public Movie createMovie(
            MovieWriteModel movieToSave
    ) {
        if (repository.existsByTitle(movieToSave.getTitle())) {
            throw new IllegalStateException("Movie with that title already exists!");
        }
        var createdAt = OffsetDateTime.now();
        Movie newMovie = movieToSave.toMovie(createdAt);

        Set<Category> categories = categoryService.checkCategories(movieToSave.getCategories());
        Set<Actor> actors = actorService.checkActors(movieToSave.getActors(), newMovie);
        // TODO: awards

        newMovie.setCategories(categories);
        newMovie.setActors(actors);

        return repository.save(newMovie);
    }

    public void insertActorToMovie(
            Set<String> newActors,
            int id
    ) {
        // FIXME: maybe no message from exception
        var movie = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        Set<Actor> actors = actorService.checkActors(newActors, movie);
        actors.forEach(actor -> movie.getActors().add(actor));
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
