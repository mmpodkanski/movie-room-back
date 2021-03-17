package io.github.mmpodkanski.movieroom.actor;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface ActorRepository extends Repository<Actor, Integer> {
    List<Actor> findAll();

    Optional<Actor> findActorById(int id);

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    Actor save(Actor entity);

    void delete(Actor entity);
}
