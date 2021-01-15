package io.github.mmpodkanski.filmroom.repository.jpa;

import io.github.mmpodkanski.filmroom.models.Actor;
import io.github.mmpodkanski.filmroom.repository.ActorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaActorRepository extends ActorRepository, JpaRepository<Actor, Integer> {
}
