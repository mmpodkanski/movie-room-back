package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findAll();

    Optional<Actor> findById(int id);

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsById(int id);

    Actor save(Actor entity);
}
