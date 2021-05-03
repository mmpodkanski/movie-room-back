package io.github.mmpodkanski.actor;

import java.util.Optional;

interface ActorRepository {

    Optional<Actor> findById(int id);

    Actor save(Actor entity);

    void deleteById(int id);
}
