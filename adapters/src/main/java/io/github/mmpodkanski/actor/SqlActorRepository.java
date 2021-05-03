package io.github.mmpodkanski.actor;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlActorRepository extends Repository<ActorSnapshot, Integer> {
    Optional<ActorSnapshot> findById(int id);

    <S extends ActorSnapshot> S save(S entity);

    void deleteById(int id);
}

interface SqlActorQueryRepository extends ActorQueryRepository, Repository<ActorSnapshot, Integer> {
}

@org.springframework.stereotype.Repository
class ActorRepositoryImpl implements ActorRepository {
    private final SqlActorRepository sqlActorRepository;

    ActorRepositoryImpl(final SqlActorRepository sqlActorRepository) {
        this.sqlActorRepository = sqlActorRepository;
    }

    @Override
    public Optional<Actor> findById(final int id) {
        return sqlActorRepository.findById(id).map(Actor::restore);
    }

    @Override
    public Actor save(final Actor entity) {
        return Actor.restore(sqlActorRepository.save(entity.getSnapshot()));
    }

    @Override
    public void deleteById(final int id) {
        sqlActorRepository.deleteById(id);
    }
}
