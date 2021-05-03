package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.DomainEventPublisher;
import io.github.mmpodkanski.actor.dto.*;
import io.github.mmpodkanski.actor.vo.ActorEvent;
import io.github.mmpodkanski.actor.vo.ActorId;
import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.user.UserFacade;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorFacade {
    private final ActorQueryRepository actorQueryRepository;
    private final ActorRepository actorRepository;
    private final ActorFactory actorFactory;
    private final UserFacade userFacade;
    private final DomainEventPublisher publisher;

    ActorFacade(
            final ActorRepository actorRepository,
            final ActorFactory actorFactory,
            final ActorQueryRepository actorQueryRepository,
            final UserFacade userFacade,
            final DomainEventPublisher publisher
    ) {
        this.actorRepository = actorRepository;
        this.actorFactory = actorFactory;
        this.actorQueryRepository = actorQueryRepository;
        this.userFacade = userFacade;
        this.publisher = publisher;
    }

    public void acceptActorsById(Set<Integer> setOfIntegers) {
        setOfIntegers.forEach(actorId ->
                actorRepository.findById(actorId).ifPresent(actor -> {
                    actor.setAcceptedByAdminToTrue();
                    actorRepository.save(actor);
                })
        );
    }

    ActorDto addActor(ActorDto newActor, String username) {
        if (actorQueryRepository.existsActorByFirstNameAndLastName(newActor.getFirstName(), newActor.getLastName())) {
            throw new ApiBadRequestException("Actor with that name already exists!");
        }
        boolean createdByAdmin = userFacade.checkIfAdmin(username);

        var actor = actorFactory.from(newActor, createdByAdmin);
        return toDto(actorRepository.save(actor));
    }

    public Set<SimpleActor> addSimpleActors(Set<ActorSimpleRequestDto> actors, boolean createdByAdmin) {
        return actors.stream().map(actorSimpleDto -> {
                    var actor = regexActor(actorSimpleDto);
                    var snapshot = actorQueryRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName())
                            .orElseGet(() -> actorRepository.save(new Actor(actor.getFirstName(), actor.getLastName(), createdByAdmin)).getSnapshot());


                    return SimpleActor.restore(new SimpleActorSnapshot(snapshot.getId(), snapshot.getFirstName(), snapshot.getLastName(), snapshot.getImageUrl()));
                }
        ).collect(Collectors.toSet());
    }

    ActorDto updateActor(int actorId, ActorDto dto) {
        if (!actorQueryRepository.existsActorById(actorId)) {
            throw new ApiNotFoundException("Actor with that id not exists!");
        }

        var simpleDto = regexActor(
                new ActorSimpleRequestDto(actorId, dto.getFirstName(), dto.getLastName()));

        var toUpdate = dto.toBuilder()
                .withId(actorId)
                .withFirstName(simpleDto.getFirstName())
                .withLastName(simpleDto.getLastName())
                .build();

        var actorToSave = actorFactory.from(toUpdate, true);

        return toDto(actorRepository.save(actorToSave));

    }

    void removeActor(int id) {
        var dto = actorQueryRepository.findDtoById(id)
                .orElseThrow(() -> new ApiNotFoundException("Actor with that id not exists!"));

        publisher.publish(new ActorEvent(new ActorId(String.valueOf(id)), new ActorEvent.Data(dto.getFirstName(), dto.getLastName(), dto.getImageUrl())));

        removeActorById(id);
    }

    private void removeActorById(int id) {
        actorRepository.findById(id)
                .ifPresent(actorRepository::delete);
    }

    private ActorSimpleRequestDto regexActor(ActorSimpleRequestDto actorSimpleRequestDTO) {
        var firstName = actorSimpleRequestDTO.getFirstName();
        var lastName = actorSimpleRequestDTO.getLastName();

        var newFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        var newLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        actorSimpleRequestDTO.update(newFirstName, newLastName);
        return actorSimpleRequestDTO;
    }


    public ActorSimpleResponseDto toSimpleDto(SimpleActor actor) {
        var snap = actor.getSnapshot();
        return ActorSimpleResponseDto.create(snap.getId(), snap.getFirstName(), snap.getLastName(), snap.getImageUrl());
    }

    private ActorDto toDto(Actor actor) {
        var snap = actor.getSnapshot();
        return ActorDto.builder()
                .withId(snap.getId())
                .withFirstName(snap.getFirstName())
                .withLastName(snap.getLastName())
                .withBirthDate(snap.getBirthDate())
                .withImageUrl(snap.getImageUrl())
                .build();
    }

}
