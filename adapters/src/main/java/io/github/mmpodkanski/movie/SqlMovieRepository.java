package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.dto.SimpleActor;
import io.github.mmpodkanski.actor.dto.SimpleActorSnapshot;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface SqlMovieRepository extends Repository<MovieSnapshot, Integer> {
    boolean existsByTitle(String title);

    List<MovieSnapshot> findAllMoviesByActorsContains(SimpleActorSnapshot actor);

    Optional<MovieSnapshot> findById(int id);

    <S extends MovieSnapshot> S save(S entity);

    void delete(MovieSnapshot entity);

}

interface SqlMovieQueryRepository extends MovieQueryRepository, Repository<MovieSnapshot, Integer> {
}

@org.springframework.stereotype.Repository
class MovieRepositoryImpl implements MovieRepository {
    private final SqlMovieRepository sqlRepository;

    MovieRepositoryImpl(final SqlMovieRepository sqlRepository) {
        this.sqlRepository = sqlRepository;
    }

    @Override
    public Optional<Movie> findById(final int id) {
        return sqlRepository.findById(id).map(Movie::restore);
    }

    @Override
    public Movie save(final Movie entity) {
        return Movie.restore(sqlRepository.save(entity.getSnapshot()));
    }

    @Override
    public void delete(final Movie entity) {
        sqlRepository.delete(entity.getSnapshot());
    }

    @Override
    public List<Movie> findAllMoviesByActorsContains(SimpleActor actor) {
        return sqlRepository.findAllMoviesByActorsContains(actor.getSnapshot()).stream().map(Movie::restore).collect(Collectors.toList());
    }
}
