package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.SimpleActor;

import java.util.Optional;

interface ActorRepository {

    Optional<Actor> findById(int id);

    Actor save(Actor entity);

    void delete(Actor entity);
}
