package io.github.mmpodkanski.movieroom.actor;

import io.github.mmpodkanski.movieroom.actor.dto.ActorRequestDto;
import io.github.mmpodkanski.movieroom.actor.dto.ActorSimpleRequestDto;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorFacade {
    private final ActorRepository repository;

    ActorFacade(final ActorRepository repository) {
        this.repository = repository;
    }

    List<ActorRequestDto> readAllActors() {
        return repository.findAll().stream()
                .map(Actor::toDto)
                .collect(Collectors.toList());
    }

    ActorRequestDto readActorById(int actorId) {
        return repository.findActorById(actorId)
                .orElseThrow(() -> new ApiNotFoundException("Acot with that id not exists!"))
                .toDto();
    }

    ActorRequestDto addActor(ActorRequestDto newActor) {
        return newActor; // TODO
    }

    public Set<Actor> returnActorsOrCreateNew(Set<ActorSimpleRequestDto> actors) {
        return actors.stream().map(actorDto -> {
                    var actor = regexActor(actorDto);
                    return repository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName())
                            .orElseGet(() -> new Actor(actor.getFirstName(), actor.getLastName()));
                }
        ).collect(Collectors.toSet());
    }

    @Transactional
    public void updateActor(int actorId, ActorRequestDto actor) {
        var actorToUpdate = repository.findActorById(actorId)
                .orElseThrow(() -> new ApiNotFoundException("Actor with that id not exists!"));

        actorToUpdate.update(actor);
    }


    public void deleteActorsFromExistingMovie(Set<Actor> actors) {
        actors.stream()
                .filter(actor -> actor.getMovies().size() <= 1)
                .forEach(repository::delete);
    }

    private ActorSimpleRequestDto regexActor(ActorSimpleRequestDto actorSimpleRequestDTO) {
        var firstName = actorSimpleRequestDTO.getFirstName();
        var lastName = actorSimpleRequestDTO.getLastName();

        var newFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        var newLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        actorSimpleRequestDTO.update(newFirstName, newLastName);
        return actorSimpleRequestDTO;
    }

}
