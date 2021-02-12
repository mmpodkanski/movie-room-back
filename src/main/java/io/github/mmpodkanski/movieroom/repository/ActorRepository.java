package io.github.mmpodkanski.movieroom.repository;

import io.github.mmpodkanski.movieroom.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findAll();

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    Actor save(Actor entity);
}
