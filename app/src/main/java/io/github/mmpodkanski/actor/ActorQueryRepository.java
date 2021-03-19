package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface ActorQueryRepository extends Repository<Actor, Integer> {

    boolean existsActorByFirstNameAndLastName(String firstName, String lastName);

    List<ActorDto> findAllBy();

    Optional<ActorDto> findDtoById(int id);

}
