package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.entity.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorRepository {
    List<Actor> findAll();

    Optional<Actor> findById(int id);

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsById(int id);

    Actor save(Actor entity);
}
