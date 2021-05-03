package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.actor.dto.SimpleActor;

import java.util.List;
import java.util.Optional;

interface ActorQueryRepository {

    boolean existsActorById(int actorId);

    boolean existsActorByFirstNameAndLastName(String firstName, String lastName);

    List<ActorDto> findAllBy();

    Optional<ActorSnapshot> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<ActorDto> findDtoById(int id);
}
