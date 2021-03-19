package io.github.mmpodkanski.actor;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ActorRepository extends Repository<Actor, Integer> {

    Optional<Actor> findById(int id);

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    Actor save(Actor entity);

    void delete(Actor entity);
}
